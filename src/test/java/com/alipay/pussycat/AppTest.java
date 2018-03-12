package com.alipay.pussycat;

import java.util.List;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.CacheManagerFactory;
import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.cache.redis.impl.RedisCacheManagerImpl;
import com.alipay.pussycat.context.PussyCatApplicationContext;
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
