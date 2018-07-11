package com.alipay.pussycat.consumer.impl;

import com.alipay.pussycat.consumer.ServiceConsume;
import com.alipay.pussycat.consumer.proxy.PYCServiceProxy;
import com.alipay.pussycat.consumer.proxy.ProxyFactory;
import com.alipay.pussycat.core.common.enums.PussycatExceptionEnum;
import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.ApplicationModel;
import com.alipay.pussycat.core.common.model.ServiceMetadata;
import com.alipay.pussycat.core.common.model.SimpleServiceConsumerModel;
import com.alipay.pussycat.core.common.register.conf.ConsumerConfig;

/**
 * @author wb-smj330392
 * @create 2018-04-28 13:53
 */
public class ServiceConsumeImpl<T> implements ServiceConsume {

    private T proxyIns;

    @Override
    public T consume(ServiceMetadata metadata) throws Exception {
        //先从本地缓存中获取
        SimpleServiceConsumerModel consumedServiceModel = ApplicationModel.instance()
                .getConsumedServiceModel(metadata.getInterfaceName());
        if (consumedServiceModel != null) {
            return (T) consumedServiceModel.getProxyObject();
        }

        //生成服务代理
        if (metadata.getItfClass() != null) {
            PYCServiceProxy pycServiceNewProxy = new PYCServiceProxy(metadata);
            return (T) pycServiceNewProxy.getServiceProxy();
        }
        throw new PussycatException(PussycatExceptionEnum.E_10002);
    }

    /**
     *
     * @param config
     * @return
     * @throws PussycatException
     */
    @Override
    public Object consume(ConsumerConfig config) throws PussycatException {
        //生成代理对象

        if (proxyIns != null) {
            return proxyIns;
        }

        synchronized (this) {
            try {
                //TODO 默认JDK
                proxyIns = (T) ProxyFactory.newProxy("JDK", config.getProxyClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return proxyIns;
        }
    }

}
