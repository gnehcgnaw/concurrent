package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用park 和 unpark 控制顺序执行
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 23:45
 */
@Slf4j
public class Example29 {

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            LockSupport.park();
                log.debug("1");
        }, "t1");

        Thread thread2 = new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(thread1);
        }, "t2");

        thread1.start();
        thread2.start();
    }
}
