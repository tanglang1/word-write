package com.word.write.controller;

import com.alibaba.fastjson.JSON;
import com.word.write.pojo.Mark;
import com.word.write.pojo.Paper;
import com.word.write.pojo.Statistics;
import com.word.write.pojo.Writea;
import com.word.write.service.MarkService;
import com.word.write.service.StatisticsService;
import com.word.write.service.WriteaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("writea")
public class WriteaController {
    @Resource
    private WriteaService writeaService;

    @Resource
    private StatisticsService statisticsService;

    @Resource
    private MarkService markService;

    @RequestMapping("showExam")
    public String showExam() {
        return "writea/examStart";
    }

    @RequestMapping("examStart")
    public String examStart(HttpServletRequest request, Model model,@RequestParam(value = "pnum", required = false) String pnum) {
        HttpSession session = request.getSession();
        Integer classid = (Integer) session.getAttribute("classid");
        session.setAttribute("pnum",pnum);
        System.err.println(pnum);
        List<Paper> list = writeaService.showExam(pnum,classid);
        session.setAttribute("listp",list);
        int index=1;
        model.addAttribute("index",index);
        model.addAttribute("flag",0);
        return "writea/examStart1";
    }

    @RequestMapping("finshiWritea")
    public String finshiWritea(Model model, @RequestParam(value = "writeword[]", required = false) ArrayList<String> writeword, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String word1=null;
        String word2=null;
        Paper paper=null;
        Writea writea = null;
        double mark1=0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        Integer classid = (Integer) session.getAttribute("classid");
        String stuid = (String)session.getAttribute("stuid");
        String pnum = (String)session.getAttribute("pnum");
        List<Paper> list = (List<Paper>) session.getAttribute("listp");
        for (int i = 0; i <writeword.size() ; i++) {
            System.err.println(writeword.get(i));
        }
        System.out.println("----------------------------");
        for (int i = 0; i <list.size() ; i++) {
            paper=list.get(i);
            word2=paper.getWord();
            System.err.println(word2);
        }
        Map<String,Object> map=new HashMap<>();
        System.out.println(writeword);
        for (int i = 0; i <writeword.size() ; i++) {
            word1=writeword.get(i);
            for (int j = 0; j <list.size() ; j++) {
                paper=list.get(i);
                word2=paper.getWord();
                if(word1.equals(word2)){
                    writea=new Writea();
                    System.out.println("111111111111111");
                    writea.setIsyes(1);
                    writea.setPid(list.get(i).getPid());
                    writea.setWriteword(word2);
                    writea.setWritedate(date);
                    writea.setStuid(stuid);
                    writea.setStuclass(classid);
                    System.out.println(list.get(i).getClassid());
                    writea.setPnum(pnum);
                    mark1 = mark1 + 10;
                    writeaService.addWritea(writea);
                    System.out.println(mark1);
                    break;
                }else {
                    writea=new Writea();
                    System.out.println("2222222222222222222222");
                    writea.setIsyes(0);
                    writea.setPid(list.get(i).getPid());
                    writea.setWriteword(word1);
                    writea.setWritedate(date);
                    writea.setStuid(stuid);
                    writea.setStuclass(classid);
                    System.out.println(list.get(i).getClassid());
                    writea.setPnum(pnum);
                    writeaService.addWritea(writea);
                    System.out.println(mark1);
                    break;
                }
            }
        }
        Mark mark = new Mark();
        mark.setIsflag(0);
        mark.setMark(mark1);
        mark.setMarkdate(date);
        mark.setPnum(pnum);
        mark.setStuclass(classid);
        mark.setStuid(stuid);
        markService.addMarkService(mark);
        model.addAttribute("mark1",mark1);
        model.addAttribute("flag",1);
        Statistics statistics=new Statistics();
        statistics.setNumber(10);
        statistics.setSdate(date);
        statisticsService.addStatistics(statistics);
        return "writea/examStart1";
    }

    @RequestMapping("showQuestion")
    public String showQuestion(HttpServletRequest request,
             @RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
             @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            Model model){
        HttpSession session = request.getSession();
        Integer classid = (Integer) session.getAttribute("classid");
        List<Paper> list=writeaService.findPaperByPnum(classid,pageNum-1,pageSize);
        model.addAttribute("feeds", list);
        model.addAttribute("total", list.size());
        model.addAttribute("feeds1", JSON.toJSONString(list));
        model.addAttribute("pageNum", pageNum-1);
        model.addAttribute("pageSize", pageSize);
        return "writea/showQuestion";
    }
    @RequestMapping("showQuestion1")
    public String showQuestion1(HttpServletRequest request){
        return "writea/showQuestion";
    }
    @RequestMapping("showQuestion2")
    @ResponseBody
    public String showQuestion2(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                               Model model){
        HttpSession session = request.getSession();
        Integer classid = (Integer) session.getAttribute("classid");
        int pageCount = (page - 1) * pageSize;
        List<Paper> list=writeaService.findPaperByPnum(classid,pageCount,pageSize);
        int count=writeaService.findPaperByPnumCount(classid);
        String json=JSON.toJSONString(list);
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":"
                + json + "}";
    }
    @RequestMapping("showHistoryWritea")
    public String showHistoryWritea(HttpServletRequest request){
        return "writea/historyWritea";
    }
    @RequestMapping("historyWritea")
    @ResponseBody
    public String historyWritea(HttpServletRequest request,
                                    @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                    Model model){
        HttpSession session = request.getSession();
        String stuid=(String) session.getAttribute("stuid");
        Integer classid = (Integer) session.getAttribute("classid");
        int pageCount = (page - 1) * pageSize;
        System.out.println(pageCount);
        List<Paper> list=writeaService.findPaperHistory(classid,stuid,pageCount,pageSize);
        int count=writeaService.findPaperHistoryCount(classid,stuid);
        String json=JSON.toJSONString(list);
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":"
                + json + "}";
    }

    @RequestMapping("showDetailQuestion")
    public String showDetailQuestion(Model model,HttpServletRequest request
            ,@RequestParam(value = "markdate", required = false) String markdate
            ,@RequestParam(value = "mid", required = false) Integer mid) {
        HttpSession session = request.getSession();
        session.setAttribute("markdate",markdate);
        session.setAttribute("mid",mid);
        return "writea/detailWritea";
    }
    @RequestMapping("detailQuestion")
    @ResponseBody
    public String detailQuestion(Model model,HttpServletRequest request
            ,@RequestParam(value = "markdate", required = false) String markdate
            ,@RequestParam(value = "isyes", required = false) Integer isyes
            ,@RequestParam(value = "mid", required = false) Integer mid) {
        HttpSession session = request.getSession();
        //.addAttribute("markdate",markdate);
        Integer classid = (Integer)session.getAttribute("classid");
        String stuid = (String)session.getAttribute("stuid");
        List<Writea> listWritea = writeaService.findWriteaByIsYes(isyes, markdate, classid,mid, stuid);
        return JSON.toJSONString(listWritea);
    }

}

