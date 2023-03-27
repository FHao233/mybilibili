package com.fhao;

import com.fhao.service.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * author: FHao
 * create time: 2023-03-18 18:04
 * description:
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
public class MyBilibiApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(MyBilibiApp.class,args);
        WebSocketService.setApplicationContext(app);
    }
}
