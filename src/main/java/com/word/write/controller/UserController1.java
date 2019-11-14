package com.word.write.controller;

import com.word.write.pojo.Statistics;
import com.word.write.pojo.Student;
import com.word.write.pojo.Teacher;
import com.word.write.service.StatisticsService;
import com.word.write.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user1")
public class UserController1 {
    public void show(HttpServletRequest request, HttpServletResponse response) {
        /*设置响应头允许ajax跨域访问*/
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
    }
    @Resource
    private UserService userService;

    @Resource
    private StatisticsService statisticsService;

    @RequestMapping("sysMenu")
    public String sysMenu(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        Integer role = (Integer) session.getAttribute("role");
        return "sys/sysMenu";
    }
    @RequestMapping("main1")
    public String main1(Model model) {
        Statistics statistics=statisticsService.findStatisticsBySdate();
        model.addAttribute("statistics", statistics);
        model.addAttribute("num", 0);
        Statistics statistics1=statisticsService.findStatisticsSum();
        if(statistics1!=null){
            model.addAttribute("sum1", statistics1.getNum());
        }
        if(statistics!=null){
            System.out.println("------->888888");
            if(statistics.getNum()>0){
                model.addAttribute("sum", statistics.getNum());
                model.addAttribute("num", 1);
                System.out.println("------->888888");
            }
        }
        return "sys/main";
    }

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request,HttpServletResponse response
            , @RequestParam(value = "username", required = false) String username
            , @RequestParam(value = "password", required = false) String password) {
        show(request, response);
        System.out.println("------------>"+username);
        Map<String,Object> map=new HashMap<String,Object>();
        HttpSession session = request.getSession();
        String sessionId =session.getId();
        map.put("sessionId ",sessionId );
        List<Student> studentList=userService.findStudentList(username,password);
        int count=0;
        if(studentList.size()==0){
            count=0;
        }else{
            session.setAttribute("login",studentList.get(0).getStuname());
            session.setAttribute("pwd",studentList.get(0).getStupwd());
            session.setAttribute("classid",studentList.get(0).getStuclass());
            session.setAttribute("stuid",studentList.get(0).getStuid());
            map.put("stuid",studentList.get(0).getStuid());
            count=studentList.size();
        }
        map.put("count",count);

        return map;
    }

    @RequestMapping("showCenter")
    public String showCenter() {
        return "sys/center";
    }
}
