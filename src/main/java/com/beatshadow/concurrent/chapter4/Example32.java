package com.beatshadow.concurrent.chapter4;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用await 和 signal 实现交替线程
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/5 00:48
 */
public class Example32 {

    private static AwaitSignal awaitSignal = new AwaitSignal(4) ;
    private static Condition conditionA = awaitSignal.newCondition() ;
    private static Condition conditionB = awaitSignal.newCondition() ;
    private static Condition conditionC = awaitSignal.newCondition() ;
    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            awaitSignal.print(conditionA,conditionB,"a");
        }, "tA");

        Thread threadB = new Thread(() -> {
            awaitSignal.print(conditionB,conditionC,"b");
        }, "tB");

        Thread threadC = new Thread(() -> {
            awaitSignal.print(conditionC,conditionA,"c");
        }, "tC");

        threadA.start();
        threadB.start();
        threadC.start();

        awaitSignal.lock();
        try{
           conditionA.signal();
        }
         finally {
            awaitSignal.unlock();
        }
    }
}

class AwaitSignal extends ReentrantLock {
    //层数
    private int loopNumber ;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    /**
     * 打印
     * @param currentCondition
     * @param nextCondition
     * @param string
     */
    public void print(Condition currentCondition ,Condition nextCondition ,String string){
        for (int i = 0; i < loopNumber; i++) {
            //上锁
            //加不加this是另一个概念
            this.lock();
            try {
                try {
                    currentCondition.await();
                    System.out.print(string);
                    nextCondition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                this.unlock();
            }
        }

    }
}
