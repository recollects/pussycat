package com.alipay.pussycat.consumer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.alipay.pussycat.common.model.ApplicationModel;
import com.alipay.pussycat.common.model.SimpleServiceConsumerModel;
import com.alipay.pussycat.publish.model.ServiceMetadata;

/**
 * @author wb-smj330392
 * @create 2018-04-28 13:53
 */
public class ServiceConsumeImpl implements ServiceConsume{

    @Override
    public Object consume(ServiceMetadata metadata) throws Exception {
        //先从本地缓存中获取
        SimpleServiceConsumerModel consumedServiceModel = ApplicationModel.instance().getConsumedServiceModel(metadata.getInterfaceName());
        if (consumedServiceModel !=null ){
            return consumedServiceModel.getProxyObject();
        }
        //生成服务代理
        if(metadata.getItfClass() != null){

        }

        return null;
    }

    /**
     * 生成服务代理对象
     */
    public static class PYCServiceProxy implements InvocationHandler{
        private final ServiceMetadata serviceConsumerMetadata;
        //private final SimpleServiceConsumerModel serviceModel;
        private final Object instance;

        public PYCServiceProxy(ServiceMetadata metadata,Class[] clazz) {
            this.serviceConsumerMetadata = metadata;
            this.instance = Proxy.newProxyInstance(metadata.getItfClass().getClassLoader(),clazz,this);

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }
}
