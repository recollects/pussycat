package com.alipay.pussycat.publish;

import com.alipay.pussycat.base.BaseJunit4Test;
import com.alipay.pussycat.publish.ServiceEventPublisher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by recollects on 18/3/12.
 */
public class ServiceEventPublisherTest extends BaseJunit4Test{

    @Autowired
    private ServiceEventPublisher serviceEventPublisher;

    @Test
    public void testPublishEvent(){
        //serviceEventPublisher.publishEvent(null);
    }
}
