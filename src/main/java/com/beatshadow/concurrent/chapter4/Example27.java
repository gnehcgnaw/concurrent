package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

/**
 * 控制顺序，先2后1
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 22:54
 */
@Slf4j
public class Example27 {
    static Object o = new Object();
    static  boolean t2runned = false ;
    public static void main(String[] args) {


        Thread thread1 = new Thread(() -> {
                synchronized (o){
                    while (!t2runned){
                        try {
                        o.wait();
                        }
                        catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                    log.debug("1");
                }
        }, "t1");

        Thread thread2 = new Thread(() -> {
            synchronized (o){
                log.debug("2");
                t2runned = true ;
                o.notify();

            }
        }, "t2");

        thread1.start();
        thread2.start();

    }
}
