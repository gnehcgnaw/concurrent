package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 21:22
 */
@Slf4j
public class TestThread8Monitor_3 {

  public static void main(String[] args) {
      Number3 n1 = new Number3();
      new Thread(()->{
          try {
              n1.a();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }).start();
      new Thread(()->{ n1.b(); }).start();
      new Thread(()->{ n1.c(); }).start();
    }

    //3  1s 1 2
    //2  3 1S 1
    //3  2 1s 1

}

@Slf4j(topic = "c.Number")
class Number3{
    public synchronized void a() throws InterruptedException {
        sleep(1);
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
    public void c() {
        log.debug("3");
    }
}