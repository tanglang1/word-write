package com.word.write.dao;

import com.word.write.pojo.Statistics;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    @Select("SELECT SUM(number) as num,sdate FROM statistics where DATE_SUB(CURDATE(), INTERVAL 6 DAY) <= date(sdate) GROUP by sdate;")
    List<Statistics> findStatistics();

    @Select("SELECT SUM(number) as num,sdate FROM statistics where DATE_SUB(CURDATE(), INTERVAL 6 DAY) <= date(sdate);")
    Statistics findStatisticsSum();

    @Select("SELECT SUM(number) as num,sdate FROM statistics where sdate >= date(now()) and sdate < DATE_ADD(date(now()),INTERVAL 1 DAY);")
    Statistics findStatisticsBySdate();

    @Insert("INSERT INTO `word_write`.`statistics` (`id`, `number`, `sdate`) VALUES (#{id}, #{number}, #{sdate});\n")
    int addStatistics(Statistics statistics);

}
