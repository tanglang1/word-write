package com.word.write.pojo;

import lombok.Data;

@Data
public class Mark {
    //分数序号
    private Integer mid;
    //学生id
    private String stuid;
    //学生班级id
    private Integer stuclass;
    //学生分数
    private Double mark;
    //分数日期
    private String markdate;
    //是否发送给家长
    private Integer isflag;
    //备注
    private Integer remark;
    //试卷号
    private String pnum;
    //关联
    private String pnum1;
    private String stuid1;
    private Integer classid;

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public void setStuid1(String stuid1) {
        this.stuid1 = stuid1;
    }

    public void setStuclass(Integer stuclass) {
        this.stuclass = stuclass;
    }

    public String getStuid1() {
        return stuid1;
    }

    public String getStuid() {
        return stuid;
    }

    public Integer getStuclass() {
        return stuclass;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public Double getMark() {
        return mark;
    }

    public Integer getIsflag() {
        return isflag;
    }

    public Integer getMid() {
        return mid;
    }

    public Integer getRemark() {
        return remark;
    }

    public String getMarkdate() {
        return markdate;
    }

    public String getPnum1() {
        return pnum1;
    }

    public void setIsflag(Integer isflag) {
        this.isflag = isflag;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public void setMarkdate(String markdate) {
        this.markdate = markdate;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public void setPnum1(String pnum1) {
        this.pnum1 = pnum1;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }
}
