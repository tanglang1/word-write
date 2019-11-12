package com.word.write.controller;

import com.alibaba.fastjson.JSON;
import com.word.write.pojo.Paper;
import com.word.write.pojo.StuClass;
import com.word.write.service.PaperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("paper")
public class PaperController {
    @Resource
    private PaperService paperService;

    //展示查询页面
    @RequestMapping("showFindPaper")
    public String showFindPaper() {
        return "paper/findPaper";
    }

    @RequestMapping("showDetailPaper")
    public String showDetailPaper(Model model,@RequestParam(value = "pnum", required = false) String pnum) {
        model.addAttribute("pnum",pnum);
        return "paper/detailPaper";
    }
    //查询数据
    @RequestMapping("findPaper")
    @ResponseBody
    public String findPaper(HttpServletRequest request, @RequestParam(value = "typeCode", required = false) Integer typeCode
            , @RequestParam(value = "pnum", required = false) String pnum
            , @RequestParam(value = "pdateStart", required = false) String pdateStart
            , @RequestParam(value = "pdateEnd", required = false) String pdateEnd
            , @RequestParam(value = "classid", required = false) Integer classid
            , @RequestParam(value = "username", required = false) String username
            , @RequestParam(value = "isidentify", required = false) Integer isidentify) {
        if(pdateStart==""){
            pdateStart=null;
        }
        System.out.println(isidentify);
        // 获取页面显示的条数
        int pageNum = Integer.parseInt(request.getParameter("limit")); //pageSize
        // 获取页面当前页
        int currPage = Integer.parseInt(request.getParameter("page")); //pageIndex
        // 计算数据库的分页从第几行开始取数
        // 假设每页显示条数为pageNum10条　当前页为第currPage3页　则(3-1)*10=20
        // sql 语句为　　select * from 表    where 条件   limit 20,10
        int pageCount = (currPage - 1) * pageNum;
        List<Paper> list = paperService.findPaperService(pnum, pdateStart, pdateEnd, classid, username,isidentify, pageCount, pageNum);
        String json = JSON.toJSONStringWithDateFormat(list, "YYYY-MM-DD");
        int count = paperService.findPaperCountService(pnum, pdateStart, pdateEnd, classid, username,isidentify);
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":"
                + json + "}";
    }

    //绑定班级数据
    @RequestMapping("findStuClass")
    @ResponseBody
    public String findStuClass() {
        StuClass stuClass = new StuClass();
        List<StuClass> list = paperService.findStuClassService();
        String json = JSON.toJSONString(list);
        // 运用"{\"code\":0,\"msg\":\"\",\"count\":1000,\"data\":"+ json +
        // "}"转换成layui数据表格的值格式
        String layuiJson = "{\"code\":0,\"msg\":\"\",\"count\":1000,\"data\":"
                + json + "}";
        System.out.println(layuiJson);
        return layuiJson;
    }

    //展示添加页面
    @RequestMapping("showAddPaper")
    public String showAddPaper() {
        return "paper/addPaper";
    }

    //添加
    @RequestMapping("addPaper")
    @ResponseBody
    public int addPaper(Paper paper, @RequestParam(value = "wordCount", required = false) int wordCount) {
        int i1 = 0;
        int count=1;
        if (paper != null) {
            Random random = new Random();
            int num = paperService.findWordaCountService();
            Set<Integer> integerSet = new HashSet<>();
            int num1=0;
            while (i1 < wordCount) {
                num1=random.nextInt(num);
                if (integerSet.add(num1)&&num1!=0) {
                    i1++;
                }
            }
            for (int value : integerSet) {
                System.out.print(value + ",");
                paper.setWordid(value);
                if(count==1){
                    paper.setIsidentify(1);
                    i1 = paperService.addPaperService(paper);
                    count=2;
                }else{
                    paper.setIsidentify(0);
                    i1 = paperService.addPaperService(paper);
                }
            }
        }
        System.out.println(i1);
        return i1;
    }
    //展示添加页面
    @RequestMapping("showGetaddPaper")
    public String showGetaddPaper() {
        return "paper/addSelectPaper";
    }
    @RequestMapping("/getaddPaper")
    @ResponseBody
    public String getaddPaper(Paper paper,
                              @RequestParam(value = "lengt", required = false) int lengt,
                              @RequestParam(value = "wordid1", required = false) String wordid1){

        String hood=wordid1.substring(wordid1.indexOf("[")+1,wordid1.lastIndexOf("]"));
        String[] leng=new String[lengt];
        leng=hood.split(",");
        int count=0;
        int count1=1;
        for(int i=0;i<leng.length;i++){
            if(count1==1){
                paper.setWordid(Integer.parseInt(leng[i]));
                paper.setIsidentify(1);
                count=paperService.addPaperService(paper);
                count1=2;
            }else{
                paper.setWordid(Integer.parseInt(leng[i]));
                paper.setIsidentify(0);
                count=paperService.addPaperService(paper);
            }
        }
        String json = JSON.toJSONString(count);
        return json;
    }
    //显示修改页面
    //根据id查询
    @RequestMapping("/showUpdPaper")
    public String showUpdSort(Model model, @RequestParam(value = "id", required = false) int id) {
        model.addAttribute("id", id);
        return "paper/updPaper";
    }

    //根据id查询显示数据
    @RequestMapping("/findPaperById")
    @ResponseBody
    public String findPaperById(@RequestParam(value = "id", required = false) int id) {
        Paper paper = paperService.findPaperByIdService(id);
        return JSON.toJSONStringWithDateFormat(paper, "YYYY-MM-DD");
    }

    //修改
    @RequestMapping("updPaper")
    @ResponseBody
    public int updPaper(Paper paper) {
        int count = 0;
        if (paper != null) {
            count = paperService.updPaperService(paper);
        }
        return count;
    }

    //删除
    @RequestMapping("delPaper")
    @ResponseBody
    public int delPaper(@RequestParam(value = "pnum", required = false) String pnum) {
        return paperService.delPaperService(pnum);
    }

    //展示查询页面
    @RequestMapping("showFind")
    public String showFind() {
        return "writea/examStart";
    }
}
