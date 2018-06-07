package com.alipay.pussycat.consumer.impl;

import com.alipay.pussycat.consumer.ServiceConsume;
import com.alipay.pussycat.consumer.proxy.PYCServiceProxy;
import com.alipay.pussycat.core.common.enums.PussycatExceptionEnum;
import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.ApplicationModel;
import com.alipay.pussycat.core.common.model.ServiceMetadata;
import com.alipay.pussycat.core.common.model.SimpleServiceConsumerModel;

/**
 * @author wb-smj330392
 * @create 2018-04-28 13:53
 */
public class ServiceConsumeImpl implements ServiceConsume {

    @Override
    public Object consume(ServiceMetadata metadata) throws Exception {
        //先从本地缓存中获取
        SimpleServiceConsumerModel consumedServiceModel = ApplicationModel.instance()
                .getConsumedServiceModel(metadata.getInterfaceName());
        if (consumedServiceModel != null) {
            return consumedServiceModel.getProxyObject();
        }

        //生成服务代理
        if (metadata.getItfClass() != null) {
            PYCServiceProxy pycServiceNewProxy = new PYCServiceProxy(metadata);
            return pycServiceNewProxy.getServiceProxy();
        }
        throw new PussycatException(PussycatExceptionEnum.E_10002);
    }

}
