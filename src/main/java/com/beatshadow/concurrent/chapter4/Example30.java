package com.beatshadow.concurrent.chapter4;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用 Park unPark 进行交替输出
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/5 00:32
 */
public class Example30 {
    private  static Thread thread1 ,thread2, thread3 ;
    public static void main(String[] args) {
        ParkUnPark parkUnPark = new ParkUnPark(4);
        thread1 = new Thread(() -> {
            parkUnPark.print("a",thread2);
        }, "t1");

        thread2 = new Thread(() -> {
            parkUnPark.print("b",thread3);
        }, "t2");

        thread3 = new Thread(() -> {
            parkUnPark.print("c",thread1);
        }, "t3");

        thread1.start();
        thread2.start();
        thread3.start();
        LockSupport.unpark(thread1);
    }
}

class ParkUnPark{
    //次数
    private int loopNumber ;

    public ParkUnPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void  print(String string , Thread nextThread ){
        for (int i = 0; i < loopNumber ; i++) {
            LockSupport.park();
            System.out.print(string);
            LockSupport.unpark(nextThread);
        }
    }
}