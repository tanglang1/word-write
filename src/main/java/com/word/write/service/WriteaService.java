package com.word.write.service;

import com.word.write.pojo.Paper;
import com.word.write.pojo.Writea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface WriteaService {
    List<Paper> findPaperByPnum(Integer classid
            ,int pageStart
            ,int pageSize);
    int findPaperByPnumCount(Integer classid);
    List<Paper> findPaperHistory( Integer classid
            ,String stuid
            ,int pageStart
            ,int pageSize);
    int findPaperHistoryCount(Integer classid
            , String stuid);
    List<Paper> showExam(String pnum, Integer classid);

    List<Writea> findWritea(Integer isyes
            , String writeDateStart
            , String writeDateEnd
            , String pnum
            , String stuclass
            , int pageStart
            , int pageSize
    );

    List<Writea> findWriteaByIsYes(Integer isyes
            , String markdate
            , Integer stuclass
            , Integer mid
            , String stuid
    );

    int findWriteaCount(Integer isyes
            , String writeDateStart
            , String writeDateEnd
            , String pnum
            , String stuclass

    );

    int countPaper(String pnum, Integer classid);

    int addWritea(Writea writea);

    int delWritea(Integer writeid);
}
