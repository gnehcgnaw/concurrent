package com.beatshadow.concurrent.chapter4;

/**
 * wait/notify 演示
 * 获得对象锁的前提下调用wait方法
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 22:42
 */
public class Example8 {
    public static void main(String[] args) {
        Object o = new Object();
        synchronized (o){
            try {
                o.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
