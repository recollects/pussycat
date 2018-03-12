package com.alipay.pussycat.cache.redis.impl;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.constant.RedisProtocolStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 *
 * 缓存redis实现
 *
 * @author jiadong
 * @date 2018/3/12 15:16
 */
public class RedisCacheManagerImpl implements CacheManager {

    @Override
    public boolean set(String key, String value) {
        Jedis jedis = JedisInstance.getInstance();
        String str = jedis.set(key, value);
        return StringUtils.equalsIgnoreCase(str, RedisProtocolStatus.SUCCESS);
    }

    @Override
    public String get(String key) {
        Jedis jedis = JedisInstance.getInstance();
        return jedis.get(key);
    }

    @Override
    public boolean del(String key) {
        Jedis jedis = JedisInstance.getInstance();
        Long del = jedis.del(key);
        if (del>0){
            return true;
        }
        return false;
    }

    @Override
    public CacheEnum cacheName() {
        return CacheEnum.REDIS;
    }

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    static class JedisInstance{

        private JedisInstance(){}

        private static volatile Jedis jedis = null;

        /**
         * 获取jedis单实例
         *
         * @return
         */
        public static Jedis getInstance(){

            if (jedis==null){
                synchronized (JedisInstance.class){
                    if (jedis==null) {
                        JedisShardInfo shardInfo = getJedisConnectionFactory().getShardInfo();
                        jedis = shardInfo.createResource();
                    }
                }
            }
            return jedis;
        }
    }

    public static JedisConnectionFactory getJedisConnectionFactory() {
        //ApplicationContext applicationContext = PussyCatSpringContextUtils.getApplicationContext();
        //return (JedisConnectionFactory)applicationContext.getBean("jedisConnectionFactory");
        return null;
    }
}
