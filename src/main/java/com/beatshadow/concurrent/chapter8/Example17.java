package com.beatshadow.concurrent.chapter8;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/16 19:44
 */
public class Example17 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2) ;

        new Thread(()->{
            System.out.println("线程1开始.."+new Date());
            try {
                cyclicBarrier.await(); // 当个数不足时，等待
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("线程1继续向下运行..."+new Date());
        }).start();

        new Thread(()->{
            System.out.println("线程2开始.."+new Date());
            try { Thread.sleep(2000); } catch (InterruptedException e) { }
            try {
                cyclicBarrier.await(); // 2 秒后，线程个数够2，继续运行
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("线程2继续向下运行..."+new Date());
        }).start();
    }
}
