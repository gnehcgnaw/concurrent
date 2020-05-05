package com.beatshadow.concurrent.chapter4;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/5 01:17
 */
public class Example31 {
    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(4,1);
        new Thread(()->{
            waitNotify.print("a",1,2);
        },"t1").start();

        new Thread(()->{
            waitNotify.print("b",2,3);
        },"t2").start();

        new Thread(()->{
            waitNotify.print("c",3,1);
        },"t3").start();

    }
}


class WaitNotify{

    private int loopNumber ;

    public WaitNotify(int loopNumber, int flag) {
        this.loopNumber = loopNumber;
        this.flag = flag;
    }

    private int flag ;

    public void  print(String string ,int waitFlag ,int nextFlag){

        for (int i = 0; i < loopNumber; i++) {
            synchronized (this){
                if (flag!=waitFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(string);
                flag = nextFlag ;
                this.notifyAll();
            }
        }

    }
}