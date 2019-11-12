package com.word.write.controller;

import com.alibaba.fastjson.JSON;
import com.word.write.dao.UserMapper;
import com.word.write.pojo.Statistics;
import com.word.write.pojo.Student;
import com.word.write.pojo.Teacher;
import com.word.write.service.StatisticsService;
import com.word.write.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private StatisticsService statisticsService;

    @RequestMapping("sysMenu")
    public String sysMenu(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer role = (Integer) session.getAttribute("role");
        model.addAttribute("role", role);
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


    @RequestMapping("showLogin")
    public String showLogin() {
        return "sys/login";
    }

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request
            , @RequestParam(value = "login", required = false) String login
            , @RequestParam(value = "pwd", required = false) String pwd
            , @RequestParam(value = "role", required = false) Integer role) {
        System.out.println(role);
        Map<String,Object> map=new HashMap<String,Object>();
        HttpSession session = request.getSession();
        session.setAttribute("role",role);
        int count=0;
        if(role==0){
            List<Teacher> teacherList=userService.findTeacherList(login,pwd);
            if(teacherList.size()==0){
                count=0;
            }else{
                session.setAttribute("login",teacherList.get(0).getTname());
                session.setAttribute("pwd",teacherList.get(0).getTpwd());
                session.setAttribute("classid",teacherList.get(0).getStuclass());
                session.setAttribute("tid",teacherList.get(0).getTid());
                count=teacherList.size();
            }
        }else if(role==1){
            List<Student> studentList=userService.findStudentList(login,pwd);
            if(studentList.size()==0){
                count=0;
            }else{
                session.setAttribute("login",studentList.get(0).getStuname());
                session.setAttribute("pwd",studentList.get(0).getStupwd());
                session.setAttribute("classid",studentList.get(0).getStuclass());
                session.setAttribute("stuid",studentList.get(0).getStuid());
                count=studentList.size();
            }
        }
        map.put("count",count);
        map.put("role",role);
        return map;
    }

    @RequestMapping("showCenter")
    public String showCenter() {
        return "sys/center";
    }
}
