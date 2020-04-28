package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 15:50
 */
@Slf4j
public class Example17 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                log.debug("洗水壶");
                Thread.sleep(5000);
                log.debug("烧开水");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "老王");


        Thread thread2 = new Thread(() -> {
            try {
                log.debug("洗茶壶");
                Thread.sleep(1000);
                log.debug("拿茶叶");
                thread1.join();
                log.debug("泡茶");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "小王");

        thread1.start();
        thread2.start();
    }
}
