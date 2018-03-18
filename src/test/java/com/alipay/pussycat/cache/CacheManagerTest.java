package com.alipay.pussycat.cache;

import com.alipay.pussycat.base.BaseJunit4Test;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.impl.RedisCacheManagerImpl;
import com.alipay.pussycat.context.PussyCatApplicationContext;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.List;

/**
 * Created by recollects on 18/3/12.
 */
public class CacheManagerTest extends BaseJunit4Test {


    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;


    @Autowired
    private CacheManager redisCacheManager;

    @Autowired
    private CacheManagerFactory cacheManagerFactory;

    @Autowired
    private PussyCatApplicationContext pussyCatApplicationContext;

    @Test
    public void testGetCacheManagerFactory() {
        CacheManager cacheManager = cacheManagerFactory.get(CacheEnum.REDIS);

        boolean result=cacheManager.set("aa","bb");
        if (result){
            System.out.println("插入成功");
        }

        String aa = cacheManager.get("aa");

        System.out.println(aa);

    }

    @Test
    public void testJedisSet(){
        JedisShardInfo shardInfo = jedisConnectionFactory.getShardInfo();
        Jedis jedis = shardInfo.createResource();

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
