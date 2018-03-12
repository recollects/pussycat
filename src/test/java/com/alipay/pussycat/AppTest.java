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

    public void testGetService(){
        System.out.println(demoService);
    }
}
