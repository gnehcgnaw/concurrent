package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * Runnable方式创建的线程
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/7 00:16
 */
@Slf4j
public class Example2 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug(Thread.currentThread().getName());
            }
        };

       Thread thread = new Thread(runnable);
       thread.setName("runnable_mode_thread");
       thread.start();
    }
}
