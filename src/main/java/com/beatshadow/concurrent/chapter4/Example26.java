package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用锁超时机制解决哲学家就餐问题
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 20:01
 */
public class Example26 {
    public static void main(String[] args) {
        Chopstick2 c1 = new Chopstick2("1");
        Chopstick2 c2 = new Chopstick2("2");
        Chopstick2 c3 = new Chopstick2("3");
        Chopstick2 c4 = new Chopstick2("4");
        Chopstick2 c5 = new Chopstick2("5");
        new Philosopher2("苏格拉底", c1, c2).start();
        new Philosopher2("柏拉图", c2, c3).start();
        new Philosopher2("亚里士多德", c3, c4).start();
        new Philosopher2("赫拉克利特", c4, c5).start();
        new Philosopher2("阿基米德", c5, c1).start();
    }

}

/**
 * 让筷子作为锁对象，又想使用ReentrantLock,那么需要extends ReentrantLock
 */
class Chopstick2 extends ReentrantLock{
    String name;

    public Chopstick2(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}

@Slf4j
class Philosopher2 extends Thread {

    
    Chopstick2 left;
    Chopstick2 right;

    public Philosopher2(String name, Chopstick2 left, Chopstick2 right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    private void eat() {
        log.debug("eating...");
        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (left.tryLock()) {
                try{
                    if (right.tryLock()){
                        try {
                            eat();
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    //释放自己手里的锁，
                    left.unlock();
                }
            }
        }
    }
}


