package com.beatshadow.concurrent.chapter4;


import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 02:13
 */
public class Example12 {
    public static void main(String[] args) {
        for (int i = 0; i < 4 ; i++) {
            People people = new People();
            people.start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Integer id : MailBoxes.getIds()){
            Postman postman = new Postman(id , id+"......email");
            postman.start();
        }

    }
}

/**
 * 居民类
 */
@Slf4j
class People extends Thread{
    @Override
    public void run() {
        GuardedObject_3 guardedObject_3 = MailBoxes.createGuardedObject_3();
        log.debug("开始收信 ID ：{}",guardedObject_3.getId());
        Object mail = guardedObject_3.get(5000);
        log.debug("收到信 ID ：{},内容：{}",guardedObject_3.getId() ,mail);
    }
}

/**
 * 邮递员类
 */
@Slf4j
class Postman extends Thread{
    //信箱ID
    private Integer id ;
    //信件内容
    private String email ;

    public Postman(int id ,String email){
        this.id = id ;
        this.email = email ;
    }

    @Override
    public void run() {
        GuardedObject_3 guardedObject_3 = MailBoxes.getGuardedObject_3(id);
        guardedObject_3.complete(email);

    }
}
class MailBoxes{

    //理解ConcurrentHashMap 和 HashTable的区别，为何这里不使用HashTable
    // ConcurrentModificationException 错误如何处理
    // 参见： https://blog.csdn.net/Jiangshan11/article/details/83038857
    private static Map<Integer,GuardedObject_3> boxes = new ConcurrentHashMap<>();

    //创建GuardedObject_3，并且给定唯一的编号
    private static int id = 0;
    public static synchronized int generateId(){
        return id++ ;
    }

    public static  GuardedObject_3 createGuardedObject_3(){
        GuardedObject_3 guardedObject_3 = new GuardedObject_3(generateId());
        boxes.put(guardedObject_3.getId(),guardedObject_3);
        return guardedObject_3 ;
    }
    //获取所有的ID
    public static Set<Integer> getIds(){
        return boxes.keySet();
    }

    //获取邮件
    public static GuardedObject_3 getGuardedObject_3(Integer id){
        //变成角度，用完之后没有用，就要从成员变量中移除，不让会无限增大
        return boxes.remove(id);
    }

}

/**
 * 相当于邮箱的小格子
 */
class GuardedObject_3{
    private int id ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GuardedObject_3(int id) {
        this.id = id;
    }

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
