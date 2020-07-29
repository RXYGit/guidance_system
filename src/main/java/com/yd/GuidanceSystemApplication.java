package com.yd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.yd.mapper")
public class GuidanceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuidanceSystemApplication.class, args);
    }

}
