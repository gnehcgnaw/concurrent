package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/13 00:34
 */
@Slf4j
public class Example6 {
    static List<String> arrayList = Arrays.asList("地三鲜","宫保鸡丁","辣子鸡");
    static Random random = new Random();
    static String cooking(){
        return arrayList.get(random.nextInt(arrayList.size()));
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(()->{
            log.debug("开始点餐");
            Future<String> future = executorService.submit(() -> {
                log.debug("做菜");
                return cooking();
            });

            try {
                log.debug("上菜：{}",future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(()->{
            log.debug("开始点餐");
            Future<String> future = executorService.submit(() -> {
                log.debug("做菜");
                return cooking();
            });

            try {
                log.debug("上菜：{}",future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

    }
}
