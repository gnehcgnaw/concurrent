package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 *
 * 撤销偏向锁【调用其他线程】
 *  使用参数：-XX:BiasedLockingStartupDelay=0
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 17:53
 */
@Slf4j
public class TestBiased4 {
    public static void main(String[] args) {
        Dept dept = new Dept() ;

        new Thread(()->{
            printInfo(dept);

            //这是为了让线程td1和td2交互执行，原因是：偏向锁和轻量级锁的前提就是线程没有交错执行。
            synchronized (TestBiased4.class){
                TestBiased4.class.notify();
            } },"td1").start();

        new Thread(()->{

            synchronized (TestBiased4.class){
                try {
                    TestBiased4.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            printInfo(dept);
            synchronized (TestBiased4.class){
                TestBiased4.class.notify();
            } },"td2").start();

    }

    private static void printInfo(Dept dept) {
        log.debug("执行synchronized前");
        System.out.println(ClassLayout.parseInstance(dept).toPrintable());
        synchronized (dept) {
            log.debug("执行synchronized中");
            System.out.println(ClassLayout.parseInstance(dept).toPrintable());
        }
        log.debug("执行synchronized后");
        System.out.println(ClassLayout.parseInstance(dept).toPrintable());
    }
}

class Dept{

}
