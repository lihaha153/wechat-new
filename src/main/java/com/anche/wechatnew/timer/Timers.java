package com.anche.wechatnew.timer;

import com.anche.wechatnew.timer.Service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class Timers {

    @Autowired
    private TimerService timerService;


    /**
     * 定时器刷新access_token、jsapi_ticket
     * access_token、jsapi_ticket的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     */
    //@Scheduled(fixedRate = 7200000)  //每隔7200秒执行一次
    public void testTasks() {
        timerService.getAccessToken();
        timerService.getJsApi_ticket();
    }
}
