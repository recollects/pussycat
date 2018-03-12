package com.alipay.pussycat;

import java.util.List;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.CacheManagerFactory;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.impl.RedisCacheManagerImpl;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.pussycat.base.BaseJunit4Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseJunit4Test{

    @Autowired
    private DemoService demoService;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;


    @Autowired
    private CacheManager redisCacheManager;

    @Autowired
    private CacheManagerFactory cacheManagerFactory;

    @Test
    public void testGetService() {
        System.out.println(demoService);
        System.out.println(jedisConnectionFactory);


        //JedisShardInfo shardInfo = jedisConnectionFactory.getShardInfo();
        //
        //Jedis jedis = shardInfo.createResource();

        CacheManager cacheManager = cacheManagerFactory.get(CacheEnum.REDIS);

        cacheManager.set("aa","bb");

        String aa = cacheManager.get("aa");

        System.out.println(aa);

        //Jedis jedis = JedisInstance.getInstance();
        //
        //String set = jedis.set("aa", "aa");
        //System.out.println(set);
        //String aa = jedis.get("aa");
        //Long aa1 = jedis.del("aa");
        //System.out.println(aa1);
        //
        //System.out.println(aa);

        //CacheEnum cacheEnum = redisCacheManager.cacheName();
        //System.out.println(cacheEnum);



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
