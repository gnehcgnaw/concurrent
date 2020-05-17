package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/13 02:08
 */
@Slf4j
public class Example9 {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("任务执行");
        },1,1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(()->{
            log.debug("任务执行");
        },1,1, TimeUnit.SECONDS);

    }
}
