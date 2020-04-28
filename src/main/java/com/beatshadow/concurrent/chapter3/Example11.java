package com.beatshadow.concurrent.chapter3;

import java.util.concurrent.TimeUnit;

/**
 * {@link Thread#join()} 等待线程死亡
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 11:09
 */
public class Example11 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread("td1") {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread2 = new Thread("td2") {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        long start = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        System.out.println(thread1.getState());
        thread1.join();
        System.out.println(thread1.getState());
        thread2.join();
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }
}
