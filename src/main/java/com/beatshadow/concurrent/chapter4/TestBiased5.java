package com.beatshadow.concurrent.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

/**
 * 重偏向
 * 使用参数：-XX:BiasedLockingStartupDelay=0
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 19:08
 */
@Slf4j
public class TestBiased5 {
    public static void main(String[] args) {
        Vector<Teacher> teacherVector = new Vector<>();
        new Thread(()->{
            for (int i = 0; i < 30 ; i++) {
                Teacher teacher = new Teacher();
                teacherVector.add(teacher);
                log.debug("执行synchronized前");
                System.out.println(ClassLayout.parseInstance(teacher).toPrintable());
                synchronized (teacher) {
                    log.debug("执行synchronized中");
                    System.out.println(ClassLayout.parseInstance(teacher).toPrintable());
                }
                log.debug("执行synchronized后");
                System.out.println(ClassLayout.parseInstance(teacher).toPrintable());

                synchronized (teacherVector){
                    teacherVector.notify();
                }
            }
        },"td1").start();

        new Thread(()->{
            synchronized (teacherVector){
                try {
                    teacherVector.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 30 ; i++) {
                Teacher teacher = teacherVector.get(i);
                log.debug("第"+i+"......执行synchronized前");
                System.out.println(ClassLayout.parseInstance(teacher).toPrintable());
                synchronized (teacher) {
                    log.debug("第"+i+"......执行synchronized中");
                    System.out.println(ClassLayout.parseInstance(teacher).toPrintable());
                }
                log.debug("第"+i+"......执行synchronized后");
                System.out.println(ClassLayout.parseInstance(teacher).toPrintable());
            }
        },"td2").start();
    }
}

class Teacher{

}