package com.beatshadow.concurrent.chapter4;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 17:22
 */
public class Example19 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try{

        }catch (Exception e){

        }finally {
            reentrantLock.unlock();
        }
    }
}
