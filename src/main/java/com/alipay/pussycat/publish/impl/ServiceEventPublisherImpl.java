package com.alipay.pussycat.publish.impl;

import com.alipay.pussycat.publish.AbstractServiceEventPublisher;
import com.alipay.pussycat.publish.model.ServiceEvent;
import org.springframework.stereotype.Service;

/**
 * 实现服务发布操作
 *
 * Created by recollects on 18/3/12.
 */
@Service
public class ServiceEventPublisherImpl extends AbstractServiceEventPublisher{

    @Override
    public void publishEvent(Class clazz) {

        //使用方需要发服务的接口类型传过来,对接口里的方法,解析出来,注册到注册中心去

    }
}
