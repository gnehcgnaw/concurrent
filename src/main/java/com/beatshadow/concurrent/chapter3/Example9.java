package com.beatshadow.concurrent.chapter3;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/27 23:04
 */
public class Example9 {
    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                while (true){
                    //业务代码
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
