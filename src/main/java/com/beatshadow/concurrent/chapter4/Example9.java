package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * wait/notify 演示
 *      验证notify和notifyAll的区别
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 22:47
 */
@Slf4j
public class Example9 {

    public static void main(String[] args) {
        Object object = new Object() ;

        new Thread(()->{
            log.debug("执行");
            synchronized (object){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("执行其他代码");
        }).start();


        new Thread(()->{
            log.debug("执行");
            synchronized (object){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("执行其他代码");
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("执行");
        synchronized (object){

           //object.notify();
            object.notifyAll();
        }
    }

}
