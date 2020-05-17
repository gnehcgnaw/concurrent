package com.beatshadow.concurrent.chapter6;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * 模拟 updateAndGet
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/10 14:26
 */
public class Example2 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger  = new AtomicInteger(1);
        updateAndGet1(atomicInteger);
        System.out.println(atomicInteger.get());
        updateAndGet2(atomicInteger,value->value*5);
        System.out.println(atomicInteger.get());

    }

    //这样做代码没有任何的通用型
    //具体的计算写死了，
    //因此要把操作作为参数传进来，这里就利用了接口的思想
    private static void updateAndGet1(AtomicInteger atomicInteger) {
        while (true){
            int i = atomicInteger.get();
            int current = i +1 ;
            boolean b = atomicInteger.compareAndSet(i, current);
            if (b){
                break;
            }
        }
    }

    private static  void updateAndGet2(AtomicInteger atomicInteger , IntUnaryOperator intUnaryOperator){
        while (true){
            int i = atomicInteger.get();
            //操作数
            int current = intUnaryOperator.applyAsInt(i);
            boolean b = atomicInteger.compareAndSet(i, current);
            if (b){
                break;
            }
        }

    }
}
