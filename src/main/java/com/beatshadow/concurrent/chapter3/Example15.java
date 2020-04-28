package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 14:08
 */
@Slf4j
public class Example15 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            log.debug("thread execute");
            LockSupport.park();
            //清除打断标记
            Thread.interrupted();
            log.debug("unpark");
            log.debug("continue to execute");
            //再次调用
            LockSupport.park();
            log.debug("seconds continue to execute ");
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
