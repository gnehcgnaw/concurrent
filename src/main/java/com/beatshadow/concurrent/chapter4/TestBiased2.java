package com.beatshadow.concurrent.chapter4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:-UseBiasedLocking 【禁用偏向锁】
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 16:47
 */
@Slf4j
public class TestBiased2 {

    public static void main(String[] args) {
        Student student = new Student();
        log.debug("执行synchronized前");
        System.out.println(ClassLayout.parseInstance(student).toPrintable());

        synchronized (student){
            log.debug("执行synchronized中");
            System.out.println(ClassLayout.parseInstance(student).toPrintable());
        }
        log.debug("执行synchronized后");
        System.out.println(ClassLayout.parseInstance(student).toPrintable());
    }
}


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Student{
    private Integer id ;
    private String name ;
}