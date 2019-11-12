package com.word.write.service.impl;

import com.word.write.dao.StatisticsMapper;
import com.word.write.pojo.Statistics;
import com.word.write.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("statisticsServiceImpl")
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;
    @Override
    public List<Statistics> findStatistics() {
        return statisticsMapper.findStatistics();
    }

    @Override
    public Statistics findStatisticsBySdate() {
        return statisticsMapper.findStatisticsBySdate();
    }

    @Override
    public int addStatistics(Statistics statistics) {
        return statisticsMapper.addStatistics(statistics);
    }

    @Override
    public Statistics findStatisticsSum() {
        return statisticsMapper.findStatisticsSum();
    }
}
