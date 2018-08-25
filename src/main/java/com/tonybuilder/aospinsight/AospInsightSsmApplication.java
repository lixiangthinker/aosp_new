package com.tonybuilder.aospinsight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tonybuilder.aospinsight.mapper")
public class AospInsightSsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AospInsightSsmApplication.class, args);
    }
}
