package com.word.write.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class Writea {
    //序号
    private Integer writeid;
    //题目号
    private Integer pid;
    //默写单词
    private String writeword;
    //默写是否正确
    private Integer isyes;
    //默写日期
    private String writedate;
    //学生ID
    private String stuid;
    //学生班级id
    private Integer stuclass;
    //试卷号
    private String  pnum;
    private Integer pid1;
    private String stuid1;
    private Integer classid;
    private String wordmeaning;
    private String word;

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public String getPnum() {
        return pnum;
    }

    public Integer getPid() {
        return pid;
    }

    public Integer getIsyes() {
        return isyes;
    }

    public Integer getPid1() {
        return pid1;
    }

    public Integer getStuclass() {
        return stuclass;
    }

    public Integer getWriteid() {
        return writeid;
    }

    public String getStuid() {
        return stuid;
    }

    public String getWritedate() {
        return writedate;
    }

    public String getWriteword() {
        return writeword;
    }

    public void setIsyes(Integer isyes) {
        this.isyes = isyes;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setPid1(Integer pid1) {
        this.pid1 = pid1;
    }

    public String getStuid1() {
        return stuid1;
    }

    public void setStuclass(Integer stuclass) {
        this.stuclass = stuclass;
    }

    public void setWordmeaning(String wordmeaning) {
        this.wordmeaning = wordmeaning;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public void setStuid1(String stuid1) {
        this.stuid1 = stuid1;
    }

    public String getWordmeaning() {
        return wordmeaning;
    }

    public void setWritedate(String writedate) {
        this.writedate = writedate;
    }

    public void setWriteid(Integer writeid) {
        this.writeid = writeid;
    }

    public void setWriteword(String writeword) {
        this.writeword = writeword;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
