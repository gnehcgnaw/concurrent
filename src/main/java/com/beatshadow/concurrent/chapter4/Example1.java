package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

/**
 * 不安全的代码
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 19:53
 */
@Slf4j
public class Example1 {
    private static int count = 0 ;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5000 ; i++) {
                count++;
            }
        }, "t1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5000 ; i++) {
                count--;
            }
        }, "t2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug(String.valueOf(count));
    }
}
