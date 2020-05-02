package com.beatshadow.concurrent.chapter4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;


/**
 *-XX:+UseBiasedLocking 【默认开启】-XX:BiasedLockingStartupDelay=0   【取消延时】
 *
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/30 01:45
 */
@Slf4j
public class TestBiased1{
    public static void main(String[] args) throws InterruptedException {
        // mark word 8
        // classpointer 4
        // 12
        Dog dog = Dog.builder().age(1).name("哈利").sex(true).build();
        String printable1 = ClassLayout.parseInstance(dog).toPrintable();
        System.out.println(printable1);

        TimeUnit.SECONDS.sleep(5);
        log.debug("延迟5秒打印结果如下所示：");
        String printable2 = ClassLayout.parseInstance(new Dog()).toPrintable();
        System.out.println(printable2);

    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Dog {
    private Integer age ;   //4
    private String name ;   //4
    private boolean sex ;   //1
    // 9
}

// 12 +9 =21 + (补)3 = 24