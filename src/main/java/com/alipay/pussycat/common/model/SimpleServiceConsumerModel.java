package com.alipay.pussycat.common.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;

import com.alipay.pussycat.consumer.model.ConsumerMethodModel;
import com.alipay.pussycat.publish.model.ServiceMetadata;
import com.google.common.collect.Maps;

/**
 * @author wb-smj330392
 * @create 2018-05-03 14:04
 */
public class SimpleServiceConsumerModel {

    public ServiceMetadata getMetadata() {
        return metadata;
    }

    public Object getProxyObject() {
        return proxyObject;
    }

    public ConsumerMethodModel getMethodModels(Method method) {
        return methodModels.get(method);
    }

    private final ServiceMetadata metadata;
    private final Object proxyObject;

    private final Map<Method, ConsumerMethodModel> methodModels = Maps.newConcurrentMap();

    public SimpleServiceConsumerModel(ServiceMetadata metadata, Object proxyObject, boolean isJdk) {
        this.metadata = metadata;
        this.proxyObject = proxyObject;

        if (proxyObject != null){
            if(isJdk){
                Class<?> proxyObjectClass = proxyObject.getClass();
                Field[] declaredFields = proxyObjectClass.getDeclaredFields();
                for(Field field : declaredFields){
                    try {
                        Method method = (Method)field.get(proxyObject);

                        methodModels.put(method,new ConsumerMethodModel(method,metadata.getInterfaceName(),metadata.getTimeout()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }



}
