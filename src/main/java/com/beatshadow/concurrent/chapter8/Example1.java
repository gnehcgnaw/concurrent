package com.beatshadow.concurrent.chapter8;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/12 16:12
 */
public class Example1 {
}


/**
 * 线程池
 */
class ThreadPool{
    //阻塞队列

    //线程集合

    //核心线程数

    //获取任务超时时间【没有任务就不执行了】

    //线程包装类
    class Worker extends Thread{
        //任务

        //run
            //1、当task不为空，执行任务
            //2、当task执行完成，从任务队列中获取任务并执行
            //执行完移除task
    }

    //执行任务
        //1、任务数没有超过核心数，创建Worker线程执行任务
                //将新创建的线程加入到线程集合
        //2、任务数量超过核心数，就将任务添加到任务队列


}
/**
 * 阻塞队列
 * @param <T>
 */
class BlockingQueue<T>{
    //1、任务队列

    //2、锁：防止一个任务被多次执行

    //3、消费者的条件变量

    //4、生产者的条件变量

    //5、容量

    //不带超时的阻塞获取
        // 1、为空  等待
        // 2、不为空  获取 返回 唤醒


    //超时阻塞获取 【poll】


    //阻塞添加
        // 1、满不满 等待
        // 2、不忙 添加  唤醒

    //获取队列大小【有时候需要队列中排队执行的任务还有几个】
}