package com.beatshadow.concurrent.chapter3;


import java.util.concurrent.TimeUnit;

/**
 *
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/8 01:18
 */
public class Example4 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true){
                System.out.println("test jconsole use");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t4");

        thread.start();

    }
}
