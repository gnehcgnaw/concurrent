package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/27 21:57
 */
@Slf4j
public class Example5 {
    public static void main(String[] args) {
        Thread td1_execute = new Thread("td1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+".....td1 execute");
            }
        };
        System.out.println( td1_execute.getState());
        td1_execute.run();
        System.out.println( td1_execute.getState());
        td1_execute.start();
        System.out.println( td1_execute.getState());

    }
}
