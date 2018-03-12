package com.alipay.pussycat.cache.redis.impl;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.JedisInstance;
import com.alipay.pussycat.cache.redis.constant.RedisProtocolStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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

}
