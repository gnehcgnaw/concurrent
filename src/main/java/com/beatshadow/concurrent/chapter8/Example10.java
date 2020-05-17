package com.beatshadow.concurrent.chapter8;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/14 15:39
 */
@Slf4j
public class Example10 {
    public static void main(String[] args) {
        MyLock myLock = new MyLock() ;

        Thread thread1 = new Thread(() -> {
            //不可重入验证 ，多加一行lock
            myLock.lock();
           /* log.debug("lock ");
            myLock.lock();
            log.debug("lock ");*/
            try{
                log.debug("lock");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                log.debug("unlocking ");
                myLock.unlock();
            }
        },"td1");


        Thread thread2 = new Thread(() -> {
            myLock.lock();
            try{
                log.debug("lock");
            }finally {
                log.debug("unlocking ");
                myLock.unlock();
            }
        },"td2");


        thread1.start();
        thread2.start();
    }
}

/**
 * 自定义锁
 *      ：这么多方法让我们自己实现就太难了，但是Java给我们提供了同步类【{@link java.util.concurrent.locks.AbstractQueuedSynchronizer}】
 */
class MyLock implements Lock {
    /**
     * 自定义同步类 extends AbstractQueuedSynchronizer    [不可重入锁，即独占锁]
     */
    class MySync extends AbstractQueuedSynchronizer{
        protected MySync() {
            super();
        }
        //获取锁【把state 状态由0改成1】
        @Override
        protected boolean tryAcquire(int arg) {
            //加锁成功 【返回true】
            //设置state
            if (compareAndSetState(0, 1)) {
                //设置线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true ;
            }
            //加锁失败 【返回false】
            return false ;
        }
        //释放锁
        @Override
        protected boolean tryRelease(int arg) {
            //state 是 volatile 修饰的，为了防止指令重排 ，将setExclusiveOwnerThread(null);写在setState(0);之前
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        //独占锁，不需要此方法
        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }
        //独占锁，不需要此方法
        @Override
        protected boolean tryReleaseShared(int arg) {
            return super.tryReleaseShared(arg);
        }
        //是否持有独占锁
        @Override
        protected boolean isHeldExclusively() {
            //如果state==1 说明持有独占锁
            return getState() == 1;
        }
        //条件变量
        public Condition newCondition(){
            //AQS 中提供了ConditionObject
            return new ConditionObject() ;
        }
        @Override
        public String toString() {
            return super.toString();
        }
    }

    private MySync mySync = new MySync() ;
    //加锁【加锁不成功会放入到队列中】
    @Override
    public void lock() {
        mySync.acquire(1);
    }

    //加锁，可打断
    @Override
    public void lockInterruptibly() throws InterruptedException {
        mySync.acquireInterruptibly(1);
    }

    //尝试加锁，【尝试一次，如果不成功，就不会死等】
    @Override
    public boolean tryLock() {
        return mySync.tryAcquire(1);
    }

    //尝试加锁，【但是带有超时时间】
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mySync.tryAcquireNanos(1,unit.toNanos(time));
    }

    //解锁
    @Override
    public void unlock() {
        mySync.release(1);
    }

    //创建条件变量
    @Override
    public Condition newCondition() {
        return mySync.newCondition();
    }
}