package com.word.write;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan(basePackages = {"com.word.write.dao"})
//@MapperScan({"com.word.write.dao.PaperMapper","com.word.write.dao.WriteaMapper","com.word.write.dao.MarkMapper"})
public class WriteApplication {

    public static void main(String[] args) {
        SpringApplication.run(WriteApplication.class, args);
    }
}
