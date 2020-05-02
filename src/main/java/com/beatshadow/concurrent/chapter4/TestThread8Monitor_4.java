package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 21:22
 */
@Slf4j
public class TestThread8Monitor_4 {

  public static void main(String[] args) {
      Number4 n1 = new Number4();
      Number4 n2 = new Number4();
      new Thread(()->{
          try {
              n1.a();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }).start();
      new Thread(()->{ n2.b(); }).start();
    }

    // 2  1S 1
}

@Slf4j(topic = "c.Number")
class Number4{
    public synchronized void a() throws InterruptedException {
        sleep(1);
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
}