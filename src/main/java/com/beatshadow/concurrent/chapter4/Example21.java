package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lock尝试打断
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 17:39
 */
@Slf4j
public class Example21 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock() ;
        Thread thread = new Thread(() -> {

            try {
               log.debug("获取锁");
                reentrantLock.lock();
                log.debug("继续执行");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        },"t1");

        thread.start();
        //尝试打断
        log.debug("尝试打断");
        thread.interrupt();


    }
}
