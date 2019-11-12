package com.word.write.dao;

import com.word.write.pojo.Paper;
import com.word.write.pojo.Student;
import com.word.write.pojo.Writea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WriteaMapper {
    List<Paper> findPaperByPnum(@Param("classid") Integer classid
            , @Param("pageStart") int pageStart
            , @Param("pageSize") int pageSize);
    int findPaperByPnumCount(@Param("classid") Integer classid);
    List<Paper> findPaperHistory(@Param("classid") Integer classid
            , @Param("stuid") String stuid
            , @Param("pageStart") int pageStart
            , @Param("pageSize") int pageSize);
    int findPaperHistoryCount(@Param("classid") Integer classid
            , @Param("stuid") String stuid);
    List<Paper> showExam(@Param("pnum") String pnum, @Param("classid") Integer classid);

    List<Writea> findWritea(@Param("isyes") Integer isyes
            , @Param("writeDateStart") String writeDateStart
            , @Param("writeDateEnd") String writeDateEnd
            , @Param("pnum") String pnum
            , @Param("stuclass") String stuclass
            , @Param("pageStart") int pageStart
            , @Param("pageSize") int pageSize
    );

    List<Writea> findWriteaByIsYes(@Param("isyes") Integer isyes
            , @Param("markdate") String markdate
            , @Param("stuclass") Integer stuclass
            , @Param("mid") Integer mid
            , @Param("stuid") String stuid
    );

    int findWriteaCount(@Param("isyes") Integer isyes
            , @Param("writeDateStart") String writeDateStart
            , @Param("writeDateEnd") String writeDateEnd
            , @Param("pnum") String pnum
            , @Param("stuclass") String stuclass

    );

    int countPaper(@Param("pnum") String pnum, @Param("classid") Integer classid);

    int addWritea(Writea writea);

    int delWritea(@Param("writeid") Integer writeid);

}
