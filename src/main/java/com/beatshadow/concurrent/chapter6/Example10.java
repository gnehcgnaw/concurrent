package com.beatshadow.concurrent.chapter6;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/11 17:16
 */
public class Example10 {
    public static void main(String[] args) {
        Unsafe unsafe = UnsafeAccessor.getUnsafe();
        Field id = null;
        try {
            id = Student2.class.getDeclaredField("id");
            Field name = Student2.class.getDeclaredField("name");
            // 获得成员变量的偏移量
            long idOffset = UnsafeAccessor.unsafe.objectFieldOffset(id);
            long nameOffset = UnsafeAccessor.unsafe.objectFieldOffset(name);

            Student2 student = new Student2();
            // 使用 cas 方法替换成员变量的值
            UnsafeAccessor.unsafe.compareAndSwapInt(student, idOffset, 0, 20);  // 返回 true
            UnsafeAccessor.unsafe.compareAndSwapObject(student, nameOffset, null, "张三"); // 返回 true

            System.out.println(student);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}


class UnsafeAccessor {
    static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    static Unsafe getUnsafe() {
        return unsafe;
    }
}

@Data
class Student2 {
    volatile int id;
    volatile String name;
}