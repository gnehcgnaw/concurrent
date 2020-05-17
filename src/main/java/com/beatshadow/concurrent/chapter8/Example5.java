package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/12 22:56
 */
@Slf4j
public class Example5 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            log.debug("1");
            int i = 1/0 ;
        });

        executorService.execute(()->{
            log.debug("2");
        });

        executorService.execute(()->{
            log.debug("3");
        });

    }
}
