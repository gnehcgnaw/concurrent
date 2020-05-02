package com.beatshadow.concurrent.chapter4;

/**
 * wait/notify 演示
 * 在没有获得对象锁的前提下调用wait方法
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 22:38
 */
public class Example7 {
    public static void main(String[] args) {
        Object object = new Object();
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
