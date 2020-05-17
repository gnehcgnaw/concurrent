package com.beatshadow.concurrent.chapter6;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/11 03:54
 */
public class Example8 {
    public static void main(String[] args) {
        Student student = new Student();
        AtomicReferenceFieldUpdater referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");
        referenceFieldUpdater.updateAndGet(student,value ->"zhangsan");
        System.out.println(referenceFieldUpdater.get(student));
    }
}

@Data
class  Student{
    //没加 volatile
    volatile String name ;
    private Integer id ;
}