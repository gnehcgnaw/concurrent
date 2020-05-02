package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 23:16
 */
@Slf4j
public class TestCorrectPostureStep1 {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (room){
                log.debug("有没有烟【{}】",hasCigarette);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("有没有烟【{}】",hasCigarette);
                if (hasCigarette) {
                    log.debug("开始干活了");
                }
            }
        },"小南").start();

        for (int i = 0; i < 30 ; i++) {
            new Thread(()->{
                synchronized (room){
                    log.debug("开始干活了");
                }
            },"其他人").start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            hasCigarette = true ;
            log.debug("烟送到了");
        },"送烟的").start();
    }
}
