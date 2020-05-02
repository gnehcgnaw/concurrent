package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 21:22
 */
@Slf4j
public class TestThread8Monitor_1 {

  public static void main(String[] args) {
      Number1 number1 = new Number1() ;

      Thread thread1 = new Thread(() -> {
          number1.a();
      }, "t1");


      Thread thread2 = new Thread(() -> {
          number1.b();
      }, "t2");

      thread1.start();
      thread2.start();
    }

    //1 2 或 2 1

}

@Slf4j
class Number1{
    public synchronized void a() {
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
}
