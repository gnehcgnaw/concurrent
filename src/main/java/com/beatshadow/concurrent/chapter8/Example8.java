package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/13 01:16
 */
@Slf4j
public class Example8 {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(()->{
            log.debug("task1");
            int i = 1/0 ;
        },1, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(()->{
            log.debug("task2");
        },1, TimeUnit.SECONDS);


    }
}
