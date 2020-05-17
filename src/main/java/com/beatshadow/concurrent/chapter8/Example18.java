package com.beatshadow.concurrent.chapter8;


import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/16 20:06
 */
public class Example18 {
    static final String ALPHA = "abcedfghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        //创建测试数据【使用一次】
        //createTestData();
        demo(
                // 创建 map 集合
                // 创建 ConcurrentHashMap 对不对？
                // () -> new HashMap<String, Integer>(),

                ()-> new ConcurrentHashMap<String ,Integer>(),

                // 进行计数
                (map, words) -> {
                    for (String word : words) {
                        /*Integer counter = map.get(word);
                        int newValue = counter == null ? 1 : counter + 1;
                        map.put(word, newValue);*/

                       /*
                        LongAdder longAdder = map.computeIfAbsent(word,(value)->new LongAdder());
                        longAdder.increment();*/

                        // 函数式编程，无需原子变量
                        map.merge(word, 1, Integer::sum);
                    }
                }
        );

    }

    /**
     * 生成测试数据
     */
    private static void createTestData() {
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }
        Collections.shuffle(list);
        for (int i = 0; i < 26; i++) {
            try (PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("temp/" + (i+1) + ".txt")))) {
                String collect = list.subList(i * count, (i + 1) * count).stream()
                        .collect(Collectors.joining("\n"));
                out.print(collect);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("temp/" + i +".txt")))) {
            while(true) {
                String word = in.readLine();
                if(word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <V> void demo(Supplier<Map<String,V>> supplier, BiConsumer<Map<String,V>,List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }

        ts.forEach(t->t.start());
        ts.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counterMap);
    }
}
