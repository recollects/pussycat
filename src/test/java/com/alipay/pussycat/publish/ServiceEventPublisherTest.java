package com.alipay.pussycat.publish;

import com.alipay.pussycat.base.BaseJunit4Test;
import com.alipay.pussycat.register.redis.service.CacheManagerFactory;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.server.model.ServiceMetadata;
import com.alipay.pussycat.register.ServerRegisterService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by recollects on 18/3/12.
 */
public class ServiceEventPublisherTest extends BaseJunit4Test{


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheManagerFactory cacheManagerFactory;

    @Test
    public void testPublishEvent()throws  Exception{

        ServerRegisterService registerService = PussycatServiceContainer.getInstance(ServerRegisterService.class);

        System.out.println(registerService);

        ServiceMetadata metadata = new ServiceMetadata();

        boolean register = registerService.register(metadata);

        Assert.assertTrue(register);

//        Thread.currentThread().join();




    }
}
