package com.alipay.pussycat.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * @author jiadong
 * @date 2018/3/12 15:25
 */
public class JedisInstance {

    private JedisInstance(){}

    @Autowired
    private static JedisConnectionFactory jedisConnectionFactory;

    public static Jedis getInstance(){
        JedisShardInfo shardInfo = jedisConnectionFactory.getShardInfo();
        Jedis jedis = shardInfo.createResource();
        return jedis;
    }


}
