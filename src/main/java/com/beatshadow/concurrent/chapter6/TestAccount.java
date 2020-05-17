package com.beatshadow.concurrent.chapter6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 对共享资源的安全访问
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/6 22:19
 */

public class TestAccount{
    public static void main(String[] args) {
        Account accountUnsafe = new AccountUnsafe(10000);
        Account.demo(accountUnsafe);

        AccountCAS accountCAS = new AccountCAS(10000);
        Account.demo(accountCAS);
    }
}
/**
 * 无锁
 */
class AccountCAS implements Account{
    private AtomicInteger balance ;

    public AccountCAS(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true){
            //获取余额的最新值
            int prve = balance.get();
            //要需改的余额
            int next = prve - amount ;
            //真正修改
            if (balance.compareAndSet(prve , next)){
                break;
            }
        }
    }
}

/**
 * 有锁
 */
class AccountUnsafe implements Account{
    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this){
            return balance;
        }
    }

    @Override
    public void withdraw(Integer amount) {
        synchronized (this){
            balance -= amount;

        }
    }
}

interface Account {
    // 获取余额
    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");
    }
}

