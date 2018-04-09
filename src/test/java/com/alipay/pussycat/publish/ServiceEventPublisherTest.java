package com.alipay.pussycat.publish;

import com.alipay.pussycat.base.BaseJunit4Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by recollects on 18/3/12.
 */
public class ServiceEventPublisherTest extends BaseJunit4Test{

    @Autowired
    private ServicePublisher servicePublisher;

    @Test
    public void testPublishEvent()throws  Exception{
        Thread.currentThread().join();
    }
}
