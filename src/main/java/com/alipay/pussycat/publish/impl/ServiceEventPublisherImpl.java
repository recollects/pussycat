package com.alipay.pussycat.publish.impl;

import com.alipay.pussycat.publish.AbstractServiceEventPublisher;
import com.alipay.pussycat.publish.exception.ServicePublishException;
import com.alipay.pussycat.publish.model.SimpleServiceModel;
import org.springframework.stereotype.Service;

/**
 * 实现服务发布操作
 * @author recollects
 * @version V1.0
 *
 */
@Service
public class ServiceEventPublisherImpl extends AbstractServiceEventPublisher{

    @Override
    public void publishService(Class clazz) {

        //使用方需要发服务的接口类型传过来,对接口里的方法,解析出来,注册到注册中心去

        logger_publish.info("测试日志打印");
    }

    @Override
    public void publish(SimpleServiceModel model) throws ServicePublishException {

    }

}
