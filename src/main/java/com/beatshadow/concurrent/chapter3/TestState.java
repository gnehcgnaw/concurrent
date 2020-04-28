package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 15:21
 */
@Slf4j
public class TestState {
    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {

        }, "t1");

        Thread thread2 = new Thread(() -> {
            while (true){

            }
        }, "t2");
        thread2.start();

        Thread thread3 = new Thread(() -> {
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3");
        thread3.start();

        Thread thread4 = new Thread(() -> {
            synchronized (TestState.class){
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t4");
        thread4.start();

        Thread thread5 = new Thread(() -> {
            synchronized (TestState.class){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t5");
        thread5.start();

        Thread thread6 = new Thread(() -> {

        }, "t6");
        thread6.start();

        log.debug(String.valueOf(thread1.getState()));
        log.debug(String.valueOf(thread2.getState()));
        log.debug(String.valueOf(thread3.getState()));
        log.debug(String.valueOf(thread4.getState()));
        log.debug(String.valueOf(thread5.getState()));
        log.debug(String.valueOf(thread6.getState()));
    }
}
