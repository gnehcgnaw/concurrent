package com.beatshadow.concurrent.chapter8;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : <a href="mailto:gnehcgnaw@gmail.com">gnehcgnaw</a>
 * @since : 2020/5/15 00:52
 */

public class Example12 {

    public static void main(String[] args) {
        PaymentDaoAndCache paymentDao = new PaymentDaoAndCache();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                paymentDao.selectById(1L,"SELECT * FROM payment where id= ");
            },"readThread-"+i).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                paymentDao.updateById(2L,"update payment set serial=555 where id = ");
            },"writeThread-"+i).start();
        }

    }


}

@Slf4j
class  PaymentDao {
    public static final String URL = "jdbc:mysql://localhost:3306/cloud2020?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "1qaz2wsx!@#";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver" ;
    public static Connection conn ;
    //1.加载驱动程序
    static {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public ResultSet selectById(Long id ,String sql ){
        Statement stmt = null;
        ResultSet rs = null ;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql+id);
            log.debug("select is success");
            return rs ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null ;
    }


    public int updateById(Long id ,String sql ){
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql+id);
            int i = ptmt.executeUpdate();
            log.debug("update row is {}",i);
            return i ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}

@Slf4j
class PaymentDaoAndCache extends PaymentDao{

    private static Map<SqlPair , Object> map = new HashMap<>();

    //加入读写锁
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //读锁
    private ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    //写锁
    private ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    @Override
    public ResultSet selectById(Long id, String sql) {
        SqlPair sqlPair = SqlPair.builder().sql(sql).args(id).build();
        readLock.lock();
        try{
            Object object = map.get(sqlPair);
            if (object!=null){
                log.debug("这是缓存");
                return (ResultSet) object ;
            }
        }finally {
            readLock.unlock();
        }

        //虽然只有一个线程进入以下程序，但是每来一个线程都会去查询一次，所以要做多重检测
        writeLock.lock();
        try{
            Object object = map.get(sqlPair);
            if (object==null){
                ResultSet resultSet = super.selectById(id, sql);
                map.put(sqlPair,resultSet);
                log.debug("第一次查询");
                return resultSet ;
            }else {
                log.debug("这是缓存");
                return (ResultSet) object ;
            }

        }finally {
            writeLock.unlock();
        }

    }

    @Override
    public int updateById(Long id, String sql) {
        writeLock.lock();
        try{
            map.clear();
            return super.updateById(id, sql);
        } finally {
            writeLock.unlock();
        }
    }
}

@Builder
@Data
class SqlPair {
    private String sql ;
    private Object args ;
}