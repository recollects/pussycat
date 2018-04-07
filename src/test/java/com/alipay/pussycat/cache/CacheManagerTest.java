package com.alipay.pussycat.cache;

import com.alipay.pussycat.base.BaseJunit4Test;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.impl.RedisCacheManagerImpl;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.context.PussyCatApplicationContext;
import com.alipay.pussycat.publish.ServiceEventPublisher;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.*;

import java.util.List;

/**
 * Created by recollects on 18/3/12.
 */
public class CacheManagerTest extends BaseJunit4Test {


    private static final Logger logger = LogDef.CACHE_DIGEST;
    private static final Logger logger_publish = LogDef.SERVICE_PUBLISH_DIGEST;
    private static final Logger common = LoggerFactory.getLogger(CacheManagerTest.class);


    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private ServiceEventPublisher serviceEventPublisher;

    //@Autowired
    //private CacheManager redisCacheManager;

    //@Autowired
    //private RedisCacheManager redisCacheManagerNew;

    @Autowired
    private CacheManagerFactory cacheManagerFactory;

    @Autowired
    private PussyCatApplicationContext pussyCatApplicationContext;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testGetCacheManagerFactory() {
        CacheManager cacheManager = cacheManagerFactory.get();

        boolean result=cacheManager.set("aa","bb");

        String aa = cacheManager.get("aa");
        if (logger.isDebugEnabled()){
            logger.debug("成功插入redis一条数据key={},value={}","aa","bb");
        }

        if (logger.isErrorEnabled()){
            logger.error("抛异常");
        }

//        serviceEventPublisher.publishEvent(null);

        logger_publish.info("test-日志打印");
        logger_publish.debug("test-日志打印");
        logger_publish.error("test-日志打印");

        common.info("common-----");
        common.debug("common-----");
        common.error("common---error");
        common.error("common---asdfasfd");
        common.error("common---ewrwqre");
        common.error("common---bafdasdfasf");
        common.error("common---wqrqwreqwreqw");
        //error.debug("aaaaaaaaaaaaaaaaaaa");
        //throw new RuntimeException("抛异常");

    }

    @Test
    public void testJedisSet(){
        JedisShardInfo shardInfo = jedisConnectionFactory.getShardInfo();
        Jedis jedis = shardInfo.createResource();

        Transaction multi = jedis.multi();

        System.out.println(multi);

        String set = jedis.set("YE01", "YE01");

        jedis.set("YE02","YE02");

        String ye01 = jedis.get("YE01");
        System.out.println("查询结果--->"+ye01);

        //执行事务
        multi.exec();

        //取消事务
        multi.discard();

        ShardedJedis shardedJedis = shardedJedisPool.getResource();

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();

        RedisConnection connection = connectionFactory.getConnection();

        connection.getNativeConnection();

    }


    @Test
    public void testFindList(){
        List<CacheManager> cacheList = Lists.newArrayList();
        cacheList.add(new RedisCacheManagerImpl());
        CacheEnum cacheEnum=CacheEnum.REDIS;
        CacheManager cacheManager = Iterators.find(cacheList.iterator(), new Predicate<CacheManager>() {

            @Override
            public boolean apply(CacheManager manager) {
                if (cacheEnum == manager.cacheName()) {
                    return true;
                }
                return false;
            }
        });
        System.out.println(cacheManager.cacheName());
    }



}
