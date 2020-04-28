package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * 优雅打断线程
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 11:58
 */
@Slf4j
public class Example13 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true){
                //认为打断线程
                if (Thread.currentThread().isInterrupted()) {
                    log.debug("线程被打断");
                    break;
                }
            }
        });
        log.debug("thread start");
        thread.start();
        log.debug("thread interrupt");
        //这时候用户线程并未结束，只是说被打断的线程的interrupt=true ，这时候需要我们根据这个状态进行人工干涉
        thread.interrupt();
    }
}
