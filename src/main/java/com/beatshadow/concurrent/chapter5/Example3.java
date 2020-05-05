package com.beatshadow.concurrent.chapter5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 可见性问题：退不出的循环
 *          解决方案 synchronized
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/5 21:40
 */
@Slf4j
public class Example3 {
    private  static boolean flag = true ;
    private static Object object = new Object() ;
    public static void main(String[] args) {
        new Thread(()->{
            while (true){
                synchronized (object){
                    if (flag){
                        return;
                    }
                }

            }
        },"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("修改flag的值，尝试让线程停止");
        synchronized (object){
            flag = false ;

        }
    }
}
