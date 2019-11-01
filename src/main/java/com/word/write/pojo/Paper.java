package com.word.write.pojo;

import lombok.Data;
@Data
public class Paper {
    //题目号
    private Integer pid;
    //试卷号
    private String pnum;
    //单词序号
    private Integer wordid;
    //试卷日期
    private String pdate;
    //考试班级
    private Integer classid;
    //出题人员
    private String username;
    //单词
    private String word;
    //单词
    private String wordmeaning;
    //班级名
    private String classname;

    public Integer getClassid() {
        return classid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getWordid() {
        return wordid;
    }

    public String getPdate() {
        return pdate;
    }

    public String getPnum() {
        return pnum;
    }

    public String getUsername() {
        return username;
    }

    public String getWord() {
        return word;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getWordmeaning() {
        return wordmeaning;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWordid(Integer wordid) {
        this.wordid = wordid;
    }

    public void setWordmeaning(String wordmeaning) {
        this.wordmeaning = wordmeaning;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
