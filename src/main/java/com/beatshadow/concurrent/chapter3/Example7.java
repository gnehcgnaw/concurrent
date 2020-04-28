package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/27 22:21
 */
@Slf4j
public class Example7 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread_execute = new Thread("td1") {
            @Override
            public void run() {
                try {
                    log.debug("thread execute");
                    Thread.sleep(2000);
                    log.debug("thread execute end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread_execute.start();
        log.debug("main thread execute state");
        Thread.sleep(1000);
        log.debug("main thread execute end");
        thread_execute.interrupt();

    }
}
