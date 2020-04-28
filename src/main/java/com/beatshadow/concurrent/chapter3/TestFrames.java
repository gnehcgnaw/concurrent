package com.beatshadow.concurrent.chapter3;

/**
 * Debug方式演示栈帧的调用关系
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/8 15:41
 */
public class TestFrames {
    public static void main(String[] args) {
        method1(10);
    }

    private static void method1(int x) {
        int y = x + 1 ;
        Object m = method2();
        System.out.println(m);

    }

    private static Object method2() {
        Object n = new Object();
        return n ;
    }
}
