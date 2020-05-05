package com.beatshadow.concurrent.chapter5;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link com.beatshadow.concurrent.chapter3.Example14}
 * 两阶段终止：
 *      使用volatile
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 13:17
 */
public class Example5 {
    public static void main(String[] args) {
        TwoPhaseTermination_2 twoPhaseTermination = new TwoPhaseTermination_2();
        //启动监控线程
        twoPhaseTermination.start();

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
class TwoPhaseTermination_2{
    private static volatile boolean flag = false ;
    private Thread monitor ;

    private static boolean isRunned =false ;
    public void start(){
        synchronized (this){
            if (isRunned){
                return;
            }
            //如果是第一次启动，启动之后更该状态
            isRunned = true ;
        }
        monitor = new Thread(() -> {
                log.debug(Thread.currentThread().getName() + "线程启动");

                while (true){
                    Thread thread = Thread.currentThread();
                    if(flag){
                        log.debug("料理后事");
                        break;
                    }else{
                        try {
                            Thread.sleep(2000); //打断1
                            log.debug("执行监控记录");    //打断2
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            //重新设置打断标记
                           // thread.interrupt();
                        }

                    }
                }
            }, "t1");
        monitor.start();
    }

    public void stop(){
        flag = true ;
        //
       //monitor.interrupt();
    }
}