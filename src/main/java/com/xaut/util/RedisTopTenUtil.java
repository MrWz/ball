package com.xaut.util;

import com.xaut.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * Author ： wangzhe
 * Description : 统计热门帖子
 * Version : 0.1
 */
@Component
public class RedisTopTenUtil {
    @Autowired
    private JedisPool jedisPool;

    public double putRedisTopTen(int postID) {
        String postUid = String.valueOf(postID);
        Jedis jedis = jedisPool.getResource();
        double nums = jedis.zincrby(Constant.TOP_TEN_KEY, 1, postUid);
        jedis.close();
        return nums;
    }

    public Set<String> getInRedisTopTen() {
        Jedis jedis = jedisPool.getResource();

        Set<String> topTen = jedis.zrange(Constant.TOP_TEN_KEY, -10, -1);
        jedis.close();
        return topTen;
    }
}
