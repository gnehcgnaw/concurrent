package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用{@link ReentrantLock#newCondition()}
 *      {@link Condition#await()}
 *      {@link Condition#signal()}
 *      解决顺序执行线程的问题
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 23:24
 */
@Slf4j
public class Example28 {
    static ReentrantLock reentrantLock = new ReentrantLock() ;
    static Condition condition = reentrantLock.newCondition() ;
    static boolean t2runned = false ;
    public static void main(String[] args) {
        new Thread(()->{
            reentrantLock.lock();
            try{
                while (!t2runned){
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }finally {
                reentrantLock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            reentrantLock.lock();
            try{
                log.debug("2");
                t2runned = true ;
                condition.signal();
            }finally {
                reentrantLock.unlock();
            }
        },"t2").start();
    }
}
