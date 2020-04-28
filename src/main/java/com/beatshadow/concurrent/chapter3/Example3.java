package com.beatshadow.concurrent.chapter3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/7 00:37
 */
@Slf4j(topic = "example3")
public class Example3 {
    public static void main(String[] args) {
        try {
            FutureTask futureTask = new FutureTask(new Callable() {
                @Override
                public Object call() throws Exception {
                    return "futureTask";
                }
            });
            Thread thread = new Thread(futureTask);
            thread.setName("futureTask mode create thread ");
            thread.start();
            log.debug((String) futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
