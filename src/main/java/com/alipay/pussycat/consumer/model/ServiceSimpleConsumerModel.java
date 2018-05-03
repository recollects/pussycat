package com.alipay.pussycat.consumer.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alipay.pussycat.publish.model.ServiceMetadata;

/**
 * @author wb-smj330392
 * @create 2018-04-29 19:09
 * 
 */
public final class ServiceSimpleConsumerModel {
    private final ServiceMetadata metadata;
    private final Object proxyObject;

    private final Map<Method, ConsumerMethodModel> methodModels = new ConcurrentHashMap<>();

    public ServiceSimpleConsumerModel(ServiceMetadata metadata, Object proxyObject) {
        this.metadata = metadata;
        this.proxyObject = proxyObject;

    }

    public ServiceMetadata getMetadata() {
        return metadata;
    }

    public Object getProxyObject() {
        return proxyObject;
    }

    public ConsumerMethodModel getMethodModel(Method method) {
        return methodModels.get(method);
    }
}
