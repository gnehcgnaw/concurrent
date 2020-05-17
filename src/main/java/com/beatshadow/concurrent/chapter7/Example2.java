package com.beatshadow.concurrent.chapter7;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/11 18:36
 */
@Slf4j
public class Example2 {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    synchronized (sdf){
                        log.debug("{}", sdf.parse("1951-04-21"));
                    }
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }).start();
        }
    }
}
