package com.beatshadow.concurrent.chapter4;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/4/28 23:06
 */
public class Example4 {
    public static void main(String[] args) {
        int i = test1();
        System.out.println(i);
    }

    public static int  test1(){
        int i = 1 ;
        i++ ;
        return  i;
    }
}
