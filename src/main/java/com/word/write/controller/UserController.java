package com.word.write.controller;

import com.word.write.dao.UserMapper;
import com.word.write.pojo.Student;
import com.word.write.pojo.Teacher;
import com.word.write.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("sysMenu")
    public String sysMenu(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer role = (Integer) session.getAttribute("role");
        model.addAttribute("role", role);


        return "sys/sysMenu";
    }


    @RequestMapping("showLogin")
    public String showLogin() {
        return "sys/login";
    }

    @RequestMapping("login")
    @ResponseBody
    public int login(HttpServletRequest request
            , @RequestParam(value = "login", required = false) String login
            , @RequestParam(value = "pwd", required = false) String pwd
            , @RequestParam(value = "role", required = false) Integer role) {
        return "sys/login";
            , @RequestParam(value = "role", required = false) Integer role){
        System.out.println(role);
        HttpSession session = request.getSession();
        session.setAttribute("role",role);
        int count=0;
        if(role==0){
            List<Teacher> teacherList=userService.findTeacherList(login,pwd);
            if(teacherList==null){
                count=0;
            }else{
                session.setAttribute("login",teacherList.get(0).getTname());
                session.setAttribute("pwd",teacherList.get(0).getTpwd());
                session.setAttribute("classid",teacherList.get(0).getStuclass());
                count=teacherList.size();
            }

        }else if(role==1){
            List<Student> studentList=userService.findStudentList(login,pwd);
            if(studentList==null){
                count=0;
            }else{
                session.setAttribute("login",studentList.get(0).getStuname());
                session.setAttribute("pwd",studentList.get(0).getStupwd());
                session.setAttribute("classid",studentList.get(0).getStuclass());
                count=studentList.size();
            }
        }
        return count;
    }
}
