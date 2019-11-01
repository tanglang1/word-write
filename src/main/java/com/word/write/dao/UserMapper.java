package com.word.write.dao;

import com.word.write.pojo.Student;
import com.word.write.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {
    public List<Teacher> findTeacherList(@Param("tname") String tname, @Param("tpwd") String tpwd);

    public List<Student> findStudentList(@Param("stuname") String stuname, @Param("stupwd") String stupwd);

}
