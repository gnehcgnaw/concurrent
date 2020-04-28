package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程运行演示
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/7 00:58
 */
@Slf4j
public class TestMultiThread {
    public static void main(String[] args) {
        new Thread(()->{
           while (true){
             log.debug("running");
           }
        },"t1").start();

        new Thread(()->{
            while (true){
                log.debug("running");
            }
        },"t2").start();

    }
}
