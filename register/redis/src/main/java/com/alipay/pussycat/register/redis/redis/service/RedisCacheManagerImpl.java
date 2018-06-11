package com.alipay.pussycat.register.redis.redis.service;
import com.alipay.pussycat.core.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.register.redis.model.CacheEnum;
import com.alipay.pussycat.register.redis.redis.constant.RedisProtocolStatus;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.*;

import java.util.List;

/**
 * 缓存redis实现
 *
 * @author jiadong
 * @date 2018/3/12 15:16
 */
public class RedisCacheManagerImpl implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManagerImpl.class);

    //    @Autowired
    private static ShardedJedisPool shardedJedisPool;

    //    @Autowired
    private static JedisConnectionFactory jedisConnectionFactory;

    //    @Autowired
    private static RedisTemplate<Object, Object> redisTemplate;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(300);
        jedisPoolConfig.setMaxTotal(6000);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxWaitMillis(1000);

        jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("");
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);

        jedisConnectionFactory.afterPropertiesSet();

        redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        //        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

        List<JedisShardInfo> jedisShardInfos = Lists.newArrayList();

        JedisShardInfo shardInfo = new JedisShardInfo("127.0.0.1", 6379);

        jedisShardInfos.add(shardInfo);
        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, jedisShardInfos);

    }

    @Override
    public boolean set(String key, String value) {
        String str = getJedis().set(key, value);
        return StringUtils.equalsIgnoreCase(str, RedisProtocolStatus.SUCCESS);
    }

    @Override
    public boolean setObj(String key, Object value) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        try {
            //TODO 这是个BUG 不是超时时间,是过期时间
            //            opsForValue.set(key, value, 20000, TimeUnit.MILLISECONDS);
            opsForValue.set(key, value);
            //            Object obj = opsForValue.get(key);
            //
            //            System.out.println("存储的Key:"+key+",存储的Value:" + obj);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String get(String key) {
        return getJedis().get(key);
    }

    @Override
    public Object getObj(String key) {
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
     *
     * @return
     */
    private Jedis getJedis() {
        return jedisConnectionFactory.getConnection().getNativeConnection();
    }

    /**
     * 后期需要可以拆分方法使用.redis提供gg种数据结构存储
     *
     * @param key
     * @param value
     */
    private void putValue(String key, Object value) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

    }

    /**
     * @return
     */
    private ShardedJedis getShardedJedis() {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        return shardedJedis;
    }

    public static void main(String[] args) throws Exception {
        //        RedisCacheManagerImpl redisCacheManager = new RedisCacheManagerImpl();
        //        ServiceMetadata metada = new ServiceMetadata();
        //        metada.setInterfaceName("com.alipay.pussycat.consumer.proxy.UserService");
        //        metada.setTimeout(3000);
        //        metada.setHost("127.0.0.1");
        //        SimpleServiceProviderModel providerModel = new SimpleServiceProviderModel(metada);
        //
        //        redisCacheManager.setObj("YE"+1, new SimpleServiceProviderModel(metada));
        //        redisCacheManager.setObj("11", "YE");
        //
        //        Object ye = redisCacheManager.getObj(new String("YE"+1));
        //        Object ye1 = redisCacheManager.getObj("11");
        //
        //        System.out.println("-----------"+ye);
        //        System.out.println(ye1);

        CacheManager cacheManager = PussycatServiceContainer.getInstance(CacheManager.class);

        //        new Thread(){
        //            @Override
        //            public void run() {
        //                cacheManager.setObj("pussycat_redis_com.alipay.pussycat.consumer.proxy.UserService#1.0.0","zzz");
        //            }
        //        }.start();

        //        Thread.sleep(1000);
        //        Object obj = cacheManager.getObj("pussycat_redis_com.alipay.pussycat.consumer.proxy.UserService#1.0.0");
        //
        //        System.out.println(obj);
    }
}