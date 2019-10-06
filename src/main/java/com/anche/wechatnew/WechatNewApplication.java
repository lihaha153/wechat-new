package com.anche.wechatnew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WechatNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatNewApplication.class, args);
    }

}
