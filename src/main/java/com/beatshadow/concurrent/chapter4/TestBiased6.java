package com.beatshadow.concurrent.chapter4;

import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

/**
 * 批量撤销
 * 使用参数：-XX:BiasedLockingStartupDelay=0
 *      调整 loopNumber =39 -----> 38， 观察main线程新创建的对象。
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 19:38
 */
public class TestBiased6 {


    static Thread thread1 ,thread2 , thread3 ;
    public static void main(String[] args) {
        int  loopNumber = 39 ;
        Vector<Tiger> tigerVector = new Vector<>() ;
        thread1 = new Thread(()->{
            for (int i = 0; i < loopNumber ; i++) {
                Tiger tiger = new Tiger() ;
                tigerVector.add(tiger);
                synchronized (tiger){
                    System.out.println(Thread.currentThread().getName()+"......"+ClassLayout.parseInstance(tiger).toPrintable());
                }
            }
            LockSupport.unpark(thread2);
        });
        thread1.start();

        //thread2
        thread2 = new Thread(()->{
            LockSupport.park();
            for (int i = 0; i < loopNumber ; i++) {
                Tiger tiger = tigerVector.get(i);
                synchronized (tiger){
                    System.out.println(Thread.currentThread().getName()+"......"+ClassLayout.parseInstance(tiger).toPrintable());
                }
            }
            LockSupport.unpark(thread3);
        });
        thread2.start();

        //thread3
        thread3 = new Thread(()->{
            LockSupport.park();
            for (int i = 0; i < loopNumber ; i++) {
                Tiger tiger = tigerVector.get(i);
                synchronized (tiger){
                    System.out.println(Thread.currentThread().getName()+"......"+ClassLayout.parseInstance(tiger).toPrintable());
                }
            }
        });
        thread3.start();

        try {
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //观察新建对象偏向情况
        System.out.println(Thread.currentThread().getName()+"......"+ClassLayout.parseInstance(new Tiger()).toPrintable());
    }


}

class  Tiger {

}