package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newFixedThreadPool
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/12 21:45
 */
@Slf4j
public class Example2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(()->{
            log.debug("1");
        });
        executorService.execute(()->{
            log.debug("2");
        });
        executorService.execute(()->{
            log.debug("3");
        });

    }
}
