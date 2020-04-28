package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * Thread方式创建线程
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/7 00:31
 */
@Slf4j(topic = "example1")
public class Example1 {
    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                log.debug("thread mode create thread");
            }
        };

        thread.start();

    }
}
