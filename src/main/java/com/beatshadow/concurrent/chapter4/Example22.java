package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可打断锁{@link ReentrantLock#lockInterruptibly()}
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 18:49
 */
@Slf4j
public class Example22 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock() ;
        Thread thread = new Thread(() -> {

            try {
                log.debug("尝试获取锁");
                reentrantLock.lockInterruptibly();
                log.debug("继续执行");
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("没有获得锁");
                return;
            }
            try{
                log.debug("获得到锁");
            }
            finally {
                reentrantLock.unlock();
            }
        },"t1");

        thread.start();
        //尝试打断
        log.debug("尝试打断");
        thread.interrupt();


    }
}
