package com.word.write.controller;

import com.alibaba.fastjson.JSON;
import com.word.write.pojo.*;
import com.word.write.service.MarkService;
import com.word.write.service.StatisticsService;
import com.word.write.service.StudentService;
import com.word.write.service.WriteaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("writea1")
public class WriteaController1 {
    public void show(HttpServletRequest request, HttpServletResponse response) {
        /*设置响应头允许ajax跨域访问*/
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
    }
    @Resource
    private WriteaService writeaService;

    @Resource
    private StatisticsService statisticsService;

    @Resource
    private StudentService studentService;

    @Resource
    private MarkService markService;

    @RequestMapping("showExam")
    public String showExam() {
        return "writea/examStart";
    }

    @RequestMapping("examStart")
    @ResponseBody
    public String examStart(HttpServletRequest request,HttpServletResponse response, Model model
            ,@RequestParam(value = "pnum", required = false) String pnum
            ,@RequestParam(value = "stuid", required = false) String stuid) {
        show(request, response);
        Student student=studentService.findStudentByIdService(stuid);
        System.err.println(pnum);
        List<Paper> list = writeaService.showExam(pnum,student.getStuclass());
        String json= JSON.toJSONString(list);
        int index=1;
        model.addAttribute("index",index);
        model.addAttribute("flag",0);
        return json;
    }

    @RequestMapping("finshiWritea")
    @ResponseBody
    public double finshiWritea(Model model
            ,@RequestParam(value = "stuid", required = false) String stuid
            ,@RequestParam(value = "pnum", required = false) String pnum
            , @RequestParam(value = "writeword", required = false) ArrayList<String> writeword, HttpServletRequest request,HttpServletResponse response) {
        show(request,response);
        System.err.println("-------->"+writeword);
        Student student=studentService.findStudentByIdService(stuid);
        String word1=null;
        String word2=null;
        Paper paper=null;
        Writea writea = null;
        double mark1=0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        Integer classid = student.getStuclass();
        List<Paper> list =  writeaService.showExam(pnum,classid);
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
                    writea.setPnum(list.get(i).getPnum());
                    mark1 = mark1 + 10;
                    writeaService.addWritea(writea);
                    System.out.println(mark1);
                    pnum=list.get(i).getPnum();
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
                    writea.setPnum(list.get(i).getPnum());
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
        int count=statisticsService.addStatistics(statistics);
        return mark1;
    }

    @RequestMapping("showQuestion")
    @ResponseBody
    public String showQuestion(HttpServletRequest request,HttpServletResponse response
            ,@RequestParam(value = "stuid", required = false) String stuid){
        show(request, response);
        System.out.println(stuid);
        Student student=studentService.findStudentByIdService(stuid);
        List<Paper> list=writeaService.findPaperByPnum1(student.getStuclass());
        String json= JSON.toJSONString(list);
        return json;
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
    public String historyWritea( HttpServletRequest request,HttpServletResponse response
            ,@RequestParam(value = "stuid", required = false) String stuid){
        show(request,response);
        Student student=studentService.findStudentByIdService(stuid);
        Integer classid = student.getStuclass();
        List<Paper> list=writeaService.findPaperHistory1(classid,stuid);
        int count=writeaService.findPaperHistoryCount(classid,stuid);
        String json=JSON.toJSONString(list);
        return json;
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
    public String detailQuestion(Model model,HttpServletRequest request,HttpServletResponse response
            ,@RequestParam(value = "stuid", required = false) String stuid
            ,@RequestParam(value = "markdate", required = false) String markdate
            ,@RequestParam(value = "isyes", required = false) Integer isyes
            ,@RequestParam(value = "mid", required = false) Integer mid) {
        show(request,response);
        Student student=studentService.findStudentByIdService(stuid);
        Integer classid = student.getStuclass();
        List<Writea> listWritea = writeaService.findWriteaByIsYes(null, markdate, classid,mid, stuid);
        return JSON.toJSONString(listWritea);
    }
    @RequestMapping("detailQuestion1")
    @ResponseBody
    public String detailQuestion1(Model model,HttpServletRequest request,HttpServletResponse response
            ,@RequestParam(value = "stuid", required = false) String stuid
            ,@RequestParam(value = "markdate", required = false) String markdate
            ,@RequestParam(value = "isyes", required = false) Integer isyes
            ,@RequestParam(value = "mid", required = false) Integer mid) {
        show(request,response);
        Student student=studentService.findStudentByIdService(stuid);
        Integer classid = student.getStuclass();
        List<Writea> listWritea = writeaService.findWriteaByIsYes(0, markdate, classid,mid, stuid);
        return JSON.toJSONString(listWritea);
    }
    @RequestMapping("detailMark")
    @ResponseBody
    public String detailMark(Model model,HttpServletRequest request,HttpServletResponse response
            ,@RequestParam(value = "stuid", required = false) String stuid) {
        show(request,response);
        Student student=studentService.findStudentByIdService(stuid);
        Integer classid = student.getStuclass();
        List<Mark> listMark = markService.findMark1(stuid);
        return JSON.toJSONString(listMark);
    }

}

