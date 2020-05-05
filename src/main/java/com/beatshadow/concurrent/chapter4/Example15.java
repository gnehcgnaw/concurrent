package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

/**
 * park 和 unpark
 *      先unpark 后 park
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 15:08
 */
@Slf4j
public class Example15 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();

        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
