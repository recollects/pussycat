package com.alipay.pussycat.publish.impl;

import com.alipay.pussycat.publish.AbstractServiceEventPublisher;
import com.alipay.pussycat.publish.model.ServiceEvent;
import org.springframework.stereotype.Service;

/**
 * 实现服务发布操作[将服务信息组装发布到redis上去.需要考虑多台机器发布同样服务]
 *
 * TODO jiadong
 * Created by recollects on 18/3/12.
 */
@Service
public class ServiceEventPublisherImpl extends AbstractServiceEventPublisher{

    @Override
    public void publishEvent(ServiceEvent event) {

    }
}
