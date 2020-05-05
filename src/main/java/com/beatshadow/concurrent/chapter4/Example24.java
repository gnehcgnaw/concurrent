package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证{@link ReentrantLock#tryLock(long, TimeUnit)} 的可打断性
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 19:08
 */
@Slf4j
public class Example24 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread thread = new Thread(() -> {

            log.debug("尝试获取锁");
            try {
                //如果超时，则获取不到锁
                if (!reentrantLock.tryLock(6,TimeUnit.SECONDS)) {
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("被打断");
                return;
            }

            try{
                log.debug("获取到锁");
            }finally {
                reentrantLock.unlock();
            }
        }, "t1");

        //reentrantLock.lock();
        thread.start();
        thread.interrupt();

    }
}
