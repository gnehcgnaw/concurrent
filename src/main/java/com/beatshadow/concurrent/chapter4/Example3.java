package com.beatshadow.concurrent.chapter4;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 21:09
 */
public class Example3 {
    public static void main(String[] args) {

        Room room = new Room() ;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.add();
            }
        }, "td1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.subtract();
            }
        }, "td2");

        thread1.start();
        thread2.start();
        System.out.println(room.getCount());

    }
}

class  Room {
    private int count = 0;

    //加法
    public void add(){
        synchronized (this){
            count ++ ;
        }
    }
    //减法
    public void subtract(){
        synchronized (this){
            count -- ;
        }
    }
    //获取
    public int getCount(){
        synchronized (this){
            return count ;
        }
    }

}
