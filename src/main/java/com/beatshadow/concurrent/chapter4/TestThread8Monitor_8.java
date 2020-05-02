package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 21:22
 */
@Slf4j
public class TestThread8Monitor_8 {

    public static void main(String[] args) {
        Number8 n1 = new Number8();
        Number8 n2 = new Number8();
        new Thread(()->{
            try {
                n1.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{ n2.b(); }).start();
    }

    // 2 1S 1
    //1 1S 2
}

@Slf4j(topic = "c.Number")
class Number8{
    public static synchronized void a() throws InterruptedException {
        sleep(1);
        log.debug("1");
    }
    public static synchronized void b() {
        log.debug("2");
    }
}