package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 演示${@link java.util.concurrent.Semaphore}
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/16 17:06
 */
@Slf4j
public class Example14 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3) ;

        for (int i = 0; i < 10  ; i++) {
            new Thread(()->{
                //获取许可
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("start");
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放许可
                    semaphore.release();
                }

            }).start();
        }
    }
}
