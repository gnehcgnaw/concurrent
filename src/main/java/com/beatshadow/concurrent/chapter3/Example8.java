package com.beatshadow.concurrent.chapter3;


/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/27 22:50
 */
public class Example8 {
    public static void main(String[] args) {
        Runnable runnable1 = () -> {
            int i = 0;
            for (;;){
                System.out.println(Thread.currentThread().getName()+"-->"+i++);
            }
        };

        Runnable runnable2 = () -> {
            int i = 0;
            for (;;){
               // Thread.yield();
                System.out.println(Thread.currentThread().getName()+"------>"+i++);
            }
        };

        Thread thread1 = new Thread(runnable1,"t1");
        Thread thread2= new Thread(runnable2,"t2");

        thread1.start();
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.start();
        thread2.setPriority(Thread.MIN_PRIORITY);
    }
}
