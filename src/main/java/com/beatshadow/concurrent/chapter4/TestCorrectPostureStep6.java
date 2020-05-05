package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * 使用ReentrantLock的条件变量，完成分区。
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 23:28
 */
@Slf4j
public class TestCorrectPostureStep6 {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    //ReentrantLock 方法实现
    static ReentrantLock reentrantLock = new ReentrantLock();
    static Condition cigaretteCondition = reentrantLock.newCondition();
    static Condition takeoutCondition = reentrantLock.newCondition() ;
    public static void main(String[] args) throws InterruptedException {

        //抽烟干活的线程
        new Thread(() -> {
            reentrantLock.lock();
            try{
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        cigaretteCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            }finally {
                reentrantLock.unlock();
            }
        }, "小南").start();


        //吃饭干活的线程
        new Thread(() -> {
            reentrantLock.lock();
            try{
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        takeoutCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            }finally {
                reentrantLock.unlock();
            }
        }, "小女").start();

        sleep(1);

        //送外卖的线程
        new Thread(() -> {
            reentrantLock.lock();
            try{
                hasTakeout = true;
                log.debug("外卖到了噢！");
                takeoutCondition.signal();
            }finally {
                reentrantLock.unlock();
            }
            synchronized (room) {

            }
        }, "送外卖的").start();
    }
}
