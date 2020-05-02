package com.beatshadow.concurrent.chapter4;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/29 22:23
 */
public class Example6 {
    public static void main(String[] args) {

    }

    private final static Object object = new Object();

    public static void method1(){
        synchronized (object){
            //同步块A
            method2();
        }
    }

    private static void method2() {
        synchronized (object){
            //同步块B
            method3();
        }
    }

    private static void method3(){
        synchronized (object){
            //同步块C
        }
    }
}
