package com.beatshadow.concurrent.chapter4;

import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:BiasedLockingStartupDelay=0
 * 测试调用hashcode
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/2 17:29
 */
public class TestBiased3 {
    public static void main(String[] args) {
        Emp emp = new Emp();
        String printable1 = ClassLayout.parseInstance(emp).toPrintable();
        System.out.println(printable1);
        //调用hashcode
        emp.hashCode();
        String printable2 = ClassLayout.parseInstance(emp).toPrintable();
        System.out.println(printable2);
    }
}


class Emp {

}