package com.alipay.pussycat.cache.redis.impl;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.constant.RedisProtocolStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.concurrent.TimeUnit;

/**
 * 缓存redis实现
 *
 * @author jiadong
 * @date 2018/3/12 15:16
 */
public class RedisCacheManagerImpl implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManagerImpl.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean set(String key, String value) {
        String str = getJedis().set(key, value);
        return StringUtils.equalsIgnoreCase(str, RedisProtocolStatus.SUCCESS);
    }

    @Override
    public boolean set(Object key, Object value) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        try {
            opsForValue.set(key,value,20, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String get(String key) {
        return getJedis().get(key);
    }

    @Override
    public Object get(Object key) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        return opsForValue.get(key);
    }

    @Override
    public boolean del(String key) {
        Long del = getJedis().del(key);
        if (del > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean del(Object key) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public CacheEnum cacheName() {
        return CacheEnum.REDIS;
    }

    /**
     * 获取jedis连接
     * @return
     */
    private Jedis getJedis(){
        return jedisConnectionFactory.getConnection().getNativeConnection();
    }

    /**
     * 后期需要可以拆分方法使用.redis提供gg种数据结构存储
     *
     * @param key
     * @param value
     */
    private void putValue(String key,Object value){
        ValueOperations opsForValue = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();


    }

    /**
     *
     * @return
     */
    private ShardedJedis getShardedJedis() {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        return shardedJedis;
    }
}
