package com.alipay.pussycat.consumer;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月07日 上午10:47
 */
public class PussycatProxyFactoryBean implements FactoryBean<Object>,InitializingBean,MethodInterceptor {

    private Object serviceProxy;

    private Class<?> serviceInterface;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.serviceProxy = new ProxyFactory(serviceInterface, this).getProxy(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
//        return invocation.getMethod().invoke(this.hessianProxy, invocation.getArguments());
        return null;
    }

    @Override
    public Object getObject() throws Exception {
        return serviceProxy;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }


}
