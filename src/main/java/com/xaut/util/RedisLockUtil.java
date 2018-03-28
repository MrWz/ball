package com.xaut.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by cvter on 2017/5/26.
 */
@Component
public class RedisLockUtil {
    @Autowired
    private JedisPool jedisPool;

    public boolean getLock(String key, int value) {
        /**
         * 锁超时时间，防止线程在入锁以后，无限的执行等待
         */
        long expireMsecs;

        /**
         * 锁等待时间，防止线程饥饿
         */
        long timeoutMsecs=System.currentTimeMillis()+3*1000;

        Jedis jedis=jedisPool.getResource();

        while(timeoutMsecs > System.currentTimeMillis()){
            expireMsecs = System.currentTimeMillis() + value + 1;
            String expiresStr = String.valueOf(expireMsecs);//锁的到期时间
            long result=jedis.setnx(key, expiresStr);
            if(result == 1){
                jedis.close();
                return true;
            }

            /**
             * 执行到这里，说明该线程未能获得锁
             * 获取redis里面的时间，如果为null,则下一次循环即可获得锁
             * 如果redis里面锁的时间不为空，则进行一个是否在有效期内的判断
             * 如果已经不是有效期，说明之前的锁有异常发生，则此线程更新该锁，并成功获得锁
             */
            String currentValueStr = jedis.get(key); //redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                jedis.getSet(key, expiresStr);
                jedis.close();
                return true;
            }
        }
        /**
         * 如过执行到这里，说明此线程在规定等待的时间内并未获得锁
         */
        jedis.close();
        return false;

//        long result=jedis.setnx(key, value);
//        if (result == 1) {
//            jedis.expire(key, expireMsecs);
//            jedis.close();
//            return true;
//        }
//        /*
//        * 在等待时间内，轮询去尝试获取锁，当获取到则退出轮询
//        * 在等待时间内为获取到，则也推出循环
//        * */
//        while (result == 0 && timeoutMsecs != 0) {
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//            }
//            result=jedis.setnx(key, value);
//            --timeoutMsecs;
//        }
//        /*
//        * 在等待时间内获得锁的拥有权
//        * */
//        if (timeoutMsecs != 0) {
//            jedis.expire(key, expireMsecs);
//            jedis.close();
//            return true;
//        }
//        /*
//        * 在等待时间内未获得锁的拥有权
//        * */
//        jedis.close();
//        return false;
    }

    public void unLock(String key) {
        Jedis jedis=jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }
}
