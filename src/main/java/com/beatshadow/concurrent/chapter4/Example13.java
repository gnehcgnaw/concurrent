package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 生产者/消费者
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/4 14:42
 */
@Slf4j
public class Example13 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(()->{
                messageQueue.put(new Message(finalI, finalI +"消息"));
            },"td"+i).start();
        }
    }
}

/**
 * 线程见通讯
 */
class MessageQueue{
    //双向链表：消息集合
    private LinkedList<Message> linkedList = new LinkedList<>();

    //消息容量
    private int capacity ;

    MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    //获取消息
    public Message take(){
        synchronized (linkedList){
            while (linkedList.isEmpty()){
                try {
                    //在作为共享的上面去等待
                    linkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //返回队列的头部获取消息返回
            Message message = linkedList.removeFirst();
            linkedList.notifyAll();
            return  message ;
        }
    }
    //存入消息
    public void put(Message message){
        synchronized (linkedList){
            while (linkedList.size()==capacity){
                try {
                    linkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            linkedList.addLast(message);
            linkedList.notifyAll();
        }
    }
}

/**
 * message
 *      要点1： final
 *      要点2： 只有get 没有 set
 */
final class Message{
    private Integer id ;
    private Object data ;

    public Message(Integer id, Object data) {
        this.id = id;
        this.data = data;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public Object getData() {
        return data;
    }
}