package com.beatshadow.concurrent.chapter4;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/29 03:42
 */
public class Example5 {
    private final static Object lock = new Object() ;
    static int count = 0;

    public static void main(String[] args) {
        synchronized (lock){
            count++ ;
        }
    }
}
