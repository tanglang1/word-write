package com.word.write.service;

import com.word.write.pojo.Statistics;

import java.util.List;

public interface StatisticsService {
    List<Statistics> findStatistics();
    Statistics findStatisticsBySdate();
    int addStatistics(Statistics statistics);
    Statistics findStatisticsSum();
}
