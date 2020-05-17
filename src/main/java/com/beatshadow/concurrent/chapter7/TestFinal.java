package com.beatshadow.concurrent.chapter7;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/12 15:47
 */
public class TestFinal {
    final static  int A =10 ;
    final static int B =Short.MAX_VALUE+1;

    final int a = 20 ;
    final int b = Integer.MAX_VALUE;

    final void test1(){

    }

}


class UseFinal1{
    public void test(){
        System.out.println(TestFinal.A);
        System.out.println(TestFinal.B);
        System.out.println(new TestFinal().a);
        System.out.println(new TestFinal().b);
        new TestFinal().test1();
    }
}

class UseFinal2{
    public void test(){
        System.out.println(TestFinal.A);
    }
}