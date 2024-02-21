package com.oumaan.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: wjj
 * @Date: 2023/11/15
 */
@SpringBootApplication(scanBasePackages = "com.oumaan")
@MapperScan("com.oumaan")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
