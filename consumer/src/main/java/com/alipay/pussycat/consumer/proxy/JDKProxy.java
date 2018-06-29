package com.alipay.pussycat.consumer.proxy;

import java.lang.reflect.InvocationHandler;

/**
 *
 * @author recollects
 * @date 2018年06月29日 下午3:35 
 * @version V1.0
 *
 */
public class JDKProxy implements Proxy{

    @Override
    public <T> T getProxy(Class<T> clazz) {
        InvocationHandler handler = new JDKInvocationHandler(clazz);

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
         T result= (T) java.lang.reflect.Proxy.newProxyInstance(contextClassLoader,new Class[]{ clazz},handler);
         return result;
    }
}
