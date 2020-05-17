package com.beatshadow.concurrent.chapter6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决ABA问题
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/10 16:55
 */
@Slf4j
public class Example5 {
    static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference("A",0);
    public static void main(String[] args) {
        //获取印章
        int stamp = atomicStampedReference.getStamp();
        log.debug("当前的版本是：{}",stamp);
        other();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug(atomicStampedReference.getReference());
        log.debug("当前的版本是：{}",atomicStampedReference.getStamp());
        //跟最新的版本号对不上，故而CAS操作失败
        log.debug("是否成功：{}",atomicStampedReference.compareAndSet("A", "C", stamp, stamp + 1));

        log.debug("当前的版本是：{}",atomicStampedReference.getStamp());
        log.debug(atomicStampedReference.getReference());
    }

    public static void other(){
        new Thread(()->{
            atomicStampedReference.compareAndSet(atomicStampedReference.getReference(),"B",atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            log.debug("当前的版本是：{}",atomicStampedReference.getStamp());

            atomicStampedReference.compareAndSet(atomicStampedReference.getReference(),"A",atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            log.debug("当前的版本是：{}",atomicStampedReference.getStamp());

        }).start();
    }
}

