package com.word.write.pojo;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

@Data
public class Statistics {
    private Integer id;
    private int number;
    private String sdate;
    private Integer num;
}
