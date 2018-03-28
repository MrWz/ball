package com.xaut.util;

import com.xaut.entity.GetRedisKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Author ： wangzhe
 * Description : 系统缓存
 * Version : 0.1
 */
@Component
public class RedisCountHotBookUtil<T extends GetRedisKey> {
    @Autowired
    private JedisPool jedisPool;

    public void clearRedis(T obj, Class<T> clazz) {
        Jedis jedis = jedisPool.getResource();

        try {
            String key = "bookUid:" + obj.getUid();
            jedis.del(key);
        } catch (Exception e) {
        } finally {
            jedis.close();
        }
    }

    public String putRedis(T obj, Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            String type = instance.getClass().toString();
            String[] str = type.split(" ");
            String[] strType = str[1].split("\\.");
            String trueType = strType[strType.length - 1];

            Jedis jedis = jedisPool.getResource();
            String key = null;
            if (StringUtils.equals(trueType, "Book")) {
                key = "bookUid:" + obj.getUid();
                byte[] bytes = ProtoStuffSerializerUtil.serialize(obj);
                // 超时缓存
                int timeout = 60 * 60;//商品缓存1小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                jedis.close();
                return result;
            } else {
                key = "userName:" + obj.getName();
                byte[] bytes = ProtoStuffSerializerUtil.serialize(obj);
                // 超时缓存
                int timeout = 60 * 60 * 24 * 7;//用户缓存7天
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                jedis.close();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getInRedis(String partKey, Class<T> clazz) {
        // redis操作逻辑
        try {
            T instance = clazz.newInstance();
            String type = instance.getClass().toString();
            String[] str = type.split(" ");
            String[] strType = str[1].split("\\.");
            String trueType = strType[strType.length - 1];

            Jedis jedis = jedisPool.getResource();
            try {
                String key = null;
                if (StringUtils.equals(trueType, "Book")) {
                    key = "bookUid:" + partKey;
                } else {
                    key = "userName:" + partKey;
                }
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    T ins = clazz.newInstance();

                    ins = ProtoStuffSerializerUtil.deserialize(bytes, clazz);
                    /**
                     * 当是在Redis中查询到的时候，则延长其的有效期
                     */
                    int timeout = 60 * 60;//一小时
                    jedis.setex(key.getBytes(), timeout, bytes);
                    // seckill被反序列化
                    return ins;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
        }
        return null;
    }
}
