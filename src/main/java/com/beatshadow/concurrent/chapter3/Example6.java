package com.beatshadow.concurrent.chapter3;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/27 22:12
 */
public class Example6 {
    public static void main(String[] args) {
        new Thread("td1"){
            @Override
            public void run() {
                try {
                    System.out.println(this.getState());
                    TimeUnit.MINUTES.sleep(1);
                    System.out.println(this.getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
