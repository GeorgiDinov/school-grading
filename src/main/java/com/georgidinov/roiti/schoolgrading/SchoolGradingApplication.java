package com.georgidinov.roiti.schoolgrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SchoolGradingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolGradingApplication.class, args);
    }

}
