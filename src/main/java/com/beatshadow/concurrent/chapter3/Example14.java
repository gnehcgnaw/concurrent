package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 13:17
 */
public class Example14 {
    public static void main(String[] args) {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twoPhaseTermination.stop();
    }
}

@Slf4j
class TwoPhaseTermination{
    private Thread monitor ;
    public void start(){
         monitor = new Thread(() -> {
                log.debug(Thread.currentThread().getName() + "线程启动");

                while (true){
                    Thread thread = Thread.currentThread();
                    if(thread.isInterrupted()){
                        log.debug("料理后事");
                        break;
                    }else{
                        try {
                            Thread.sleep(2000); //打断1
                            log.debug("执行监控记录");    //打断2
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            //重新设置打断标记
                            thread.interrupt();
                        }

                    }
                }
            }, "t1");
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }
}