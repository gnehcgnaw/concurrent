package com.beatshadow.concurrent.chapter6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ABA问题的演示
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/10 15:54
 */
@Slf4j
public class Example4 {
    static AtomicReference<String> atomicReference = new AtomicReference<>("A");
    public static void main(String[] args) {

        log.debug("主线程开启");
        String perv = atomicReference.get();
        log.debug(perv);
        other();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("change A ->C：{}",atomicReference.compareAndSet(perv,"c"));
    }

    public static void other(){
        new Thread(()->{
            String perv = atomicReference.get();
            log.debug(perv);
            log.debug("change A ->B：{}",atomicReference.compareAndSet(perv, "B"));
            String perv2 = atomicReference.get();
            log.debug(perv2);
            log.debug("change B ->A：{}",atomicReference.compareAndSet(perv2, "A"));
        }).start();
    }
}

