package com.beatshadow.concurrent.chapter6;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/10 13:52
 */
public class Example1 {
    public static void main(String[] args) {
        //创建AtomicInteger, 默认值为0
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //添加并获取，此时为1
        System.out.println(atomicInteger.addAndGet(1));
        // 2
        System.out.println(atomicInteger.updateAndGet(value -> value + 1));
        //2
        System.out.println(atomicInteger.getAndUpdate(value -> value + 1));

        //6
        System.out.println(atomicInteger.updateAndGet((value) -> value * 2));
    }
}
