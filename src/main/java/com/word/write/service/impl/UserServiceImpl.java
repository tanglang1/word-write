package com.word.write.service.impl;

import com.word.write.dao.UserMapper;
import com.word.write.pojo.Student;
import com.word.write.pojo.Teacher;
import com.word.write.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Teacher> findTeacherList(String tname, String tpwd) {
        return userMapper.findTeacherList(tname, tpwd);
    }

    @Override
    public List<Student> findStudentList(String stuname, String stupwd) {
        return userMapper.findStudentList(stuname, stupwd);
    }
}
