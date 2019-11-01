package com.word.write.service;

import com.word.write.pojo.Student;
import com.word.write.pojo.Teacher;

import java.util.List;

public interface UserService {
    public List<Teacher> findTeacherList(String tname,String tpwd);

    public List<Student> findStudentList(String stuname,String stupwd);

}
