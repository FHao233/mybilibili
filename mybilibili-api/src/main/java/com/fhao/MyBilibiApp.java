package com.fhao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * author: FHao
 * create time: 2023-03-18 18:04
 * description:
 */
@SpringBootApplication
public class MyBilibiApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(MyBilibiApp.class,args);

    }
}
