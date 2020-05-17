package com.beatshadow.concurrent.chapter6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/10 20:03
 */
@Slf4j
public class Example6 {
    public static void main(String[] args) throws InterruptedException {
        GarbageBag garbageBag = new GarbageBag("垃圾桶已满。");

        AtomicMarkableReference<GarbageBag> atomicMarkableReference = new AtomicMarkableReference<>(garbageBag,true);
        log.debug("主线程 start...");
        GarbageBag prev = atomicMarkableReference.getReference();
        log.debug(prev.toString());

        new Thread(() -> {
            log.debug("打扫卫生的线程 start...");
            garbageBag.setDesc("空垃圾袋");
            while (!atomicMarkableReference.compareAndSet(garbageBag, garbageBag, true, false)) {}
            log.debug(garbageBag.toString());
        }).start();

        Thread.sleep(1000);
        log.debug("主线程想换一只新垃圾袋？");
        boolean success = atomicMarkableReference.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        log.debug("换了么？" + success);

        log.debug(atomicMarkableReference.getReference().toString());
    }
}


/**
 * 垃圾袋
 */
class GarbageBag {
    String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}