package com.beatshadow.concurrent.chapter6;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/10 14:47
 */
public class Example3 {
    public static void main(String[] args) {
        DecimalAccount.demo(new DecimalAccountImpl(new BigDecimal("10000")));
    }
}

class  DecimalAccountImpl implements DecimalAccount{
    //当前账户额度
    private AtomicReference<BigDecimal> balance ;

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    //有参数的构造，为了传入需要的值
    public DecimalAccountImpl(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        balance.updateAndGet(balance -> balance.subtract(new BigDecimal(10)));

    }
}

/**
 *  Decimal 账户接口实现
 */
interface DecimalAccount {
    // 获取余额
    BigDecimal getBalance();

    // 取款
    void withdraw(BigDecimal amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
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
        System.out.println(account.getBalance());
    }
}