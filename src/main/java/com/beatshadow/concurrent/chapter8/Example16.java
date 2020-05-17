package com.beatshadow.concurrent.chapter8;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/16 19:16
 */
@Slf4j
public class Example16 {
    public static void main(String[] args) {
        //模拟普通调用，
        //  test1();

        //
        //test2();
        test3();
    }

    /**
     * 原始方式，效率低，执行时间长
     */
    private static void test1() {
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> r1 = restTemplate.getForObject("http://localhost:8080/order/{1}", Map.class, 1);
        Map<String, Object> r2 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 1);
        Map<String, Object> r3 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 2);
        Map<String, Object> r4 = restTemplate.getForObject("http://localhost:8080/logistics/{1}", Map.class, 1);
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
        log.debug("used time is {}",System.currentTimeMillis()-start);
    }

    /**
     * 使用CountDownLatch完成
     *      多线程效率提高
     *      缺点：CountDownLatch 没有返回值，如果用返回值，请参考Future
     */
    private static void test2(){
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        CountDownLatch countDownLatch = new CountDownLatch(4);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(()->{
            Map<String, Object> r1 = restTemplate.getForObject("http://localhost:8080/order/{1}", Map.class, 1);
            System.out.println(r1);
            countDownLatch.countDown();
        });

        executorService.submit(()->{
            Map<String, Object> r2 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 1);
            System.out.println(r2);
            countDownLatch.countDown();
        });

        executorService.submit(()->{
            Map<String, Object> r3 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 2);
            System.out.println(r3);
            countDownLatch.countDown();
        });

        executorService.submit(()->{
            Map<String, Object> r4 = restTemplate.getForObject("http://localhost:8080/logistics/{1}", Map.class, 1);
            System.out.println(r4);
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
            executorService.shutdown();
            log.debug("used time is {}",System.currentTimeMillis()-start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private static void test3(){
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        CountDownLatch countDownLatch = new CountDownLatch(4);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<?> submit1 = executorService.submit(() -> {
            Map<String, Object> r1 = restTemplate.getForObject("http://localhost:8080/order/{1}", Map.class, 1);
            return r1;
        });

        Future<?> submit2 = executorService.submit(() -> {
            Map<String, Object> r2 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 1);
            return r2;
        });

        Future<?> submit3 = executorService.submit(() -> {
            Map<String, Object> r3 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 2);
            return r3;
        });

        Future<?> submit4 = executorService.submit(() -> {
            Map<String, Object> r4 = restTemplate.getForObject("http://localhost:8080/logistics/{1}", Map.class, 1);
            return r4;
        });

        try {
            System.out.println(submit1.get());
            System.out.println(submit2.get());
            System.out.println(submit3.get());
            System.out.println(submit4.get());
            executorService.shutdown();
            log.debug("used time is {}",System.currentTimeMillis()-start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}


@RestController
class TestCountDownlatchController {

    @GetMapping("/order/{id}")
    public Map<String, Object> order(@PathVariable int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("total", "2300.00");
        sleep(2000);
        return map;
    }

    @GetMapping("/product/{id}")
    public Map<String, Object> product(@PathVariable int id) {
        HashMap<String, Object> map = new HashMap<>();
        if (id == 1) {
            map.put("name", "小爱音箱");
            map.put("price", 300);
        } else if (id == 2) {
            map.put("name", "小米手机");
            map.put("price", 2000);
        }
        map.put("id", id);
        sleep(1000);
        return map;
    }

    @GetMapping("/logistics/{id}")
    public Map<String, Object> logistics(@PathVariable int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", "中通快递");
        sleep(2500);
        return map;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}