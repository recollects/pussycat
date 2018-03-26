package com.alipay.pussycat.cache.redis.impl;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.constant.RedisProtocolStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 缓存redis实现
 *
 * @author jiadong
 * @date 2018/3/12 15:16
 */
public class RedisCacheManagerImpl implements CacheManager {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public boolean set(String key, String value) {
        String str = getShardedJedis().set(key, value);
        return StringUtils.equalsIgnoreCase(str, RedisProtocolStatus.SUCCESS);
    }

    @Override
    public String get(String key) {
        return getShardedJedis().get(key);
    }

    @Override
    public boolean del(String key) {
        Long del = getShardedJedis().del(key);
        if (del > 0) {
            return true;
        }
        return false;
    }

    @Override
    public CacheEnum cacheName() {
        return CacheEnum.REDIS;
    }

    /**
     * @return
     */
    private ShardedJedis getShardedJedis() {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        return shardedJedis;
    }

}
