package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 保护性暂停示例1
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 00:57
 */
@Slf4j
public class Example10 {
    public static void main(String[] args) {
        GuardedObject_1 guardedObject_1 = new GuardedObject_1() ;
        new Thread(()->{
            log.debug("等待结果");
            Object o = guardedObject_1.get();
            ArrayList<String> stringArrayList = (ArrayList<String>) o ;
            log.debug("list size is {}",stringArrayList.size());
        },"td1").start();

        new Thread(()->{
            log.debug("生产信息");
            guardedObject_1.complete(DownLoader.download());
        },"td2").start();
    }
}


class GuardedObject_1{
    private Object response ;

    //获取
    public Object get(){
        synchronized (this){
            while (response==null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response ;
        }
    }

    //放入
    public void complete(Object response){
        synchronized (this){
            this.response = response ;
            this.notifyAll();
        }
    }
}

/**
 * 下载
 */
class DownLoader{
    public static List<String> download(){
        ArrayList<String> lines = null ;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)new URL("https://www.baidu.com").openConnection();
            lines = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line ;
            while ((line = bufferedReader.readLine())!=null){
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines ;
    }
}
