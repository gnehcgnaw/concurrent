package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 保护性暂停示例2
 *      在1的基础上添加超时处理
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 01:07
 */
@Slf4j
public class Example11 {
    public static void main(String[] args) {
        GuardedObject_2 guardedObject_2 = new GuardedObject_2() ;
        new Thread(()->{
            log.debug("等待结果");
            Object o = guardedObject_2.get(2000);
            log.debug("结果是:{}",o);

        },"td1").start();

        new Thread(()->{
            log.debug("生产信息");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardedObject_2.complete(new Object());
        },"td2").start();
    }
}


class GuardedObject_2{

    private Object response ;

    public Object get(){
       synchronized (this){
           while (response == null) {
               try {
                   wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           return response ;
       }
    }

    public void complete(Object response){
        synchronized (this){
            this.response = response ;
            this.notifyAll();
        }
    }


    /**
     * 获取超时
     * {@link Thread#join(long)}
     */
    public Object get(long timeOut) {
        synchronized (this){
            //开始时刻
            long base = System.currentTimeMillis();
            //当前程序运行了多久
            long now = 0 ;
            if (timeOut<0){
                throw new IllegalArgumentException("timeout value is negative");
            }
            if (timeOut==0){
                return get();
            }else {
                while (response==null){
                    try {
                        //应该等待的时间
                        long delay = timeOut - now ;
                        //但应该等待的时间小于等于0，就可以退出程序
                        if (delay<=0){
                            break;
                        }
                        this.wait(delay);
                        //当前程序运行了多久 = 当前系统时间 - 开始时刻
                        now = System.currentTimeMillis()-base ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return response ;
            }
        }
    }

}