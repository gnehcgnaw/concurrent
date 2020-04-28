package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 14:45
 */
@Slf4j
public class Example16 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    break;
                }
            }
        });
        //设置当前线程为守护线程，默认为false
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(1000);
            log.debug("main execute finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
