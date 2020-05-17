package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/12 22:34
 */
@Slf4j
public class Example3 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread new_thread_ = new Thread(r,"beatshadow-thread-" + atomicInteger.getAndIncrement());
                return new_thread_;
            }
        });
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
