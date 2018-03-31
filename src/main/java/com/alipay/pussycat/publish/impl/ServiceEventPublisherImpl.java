package com.alipay.pussycat.publish.impl;

import com.alipay.pussycat.publish.AbstractServiceEventPublisher;
import com.alipay.pussycat.publish.exception.ServicePublishException;
import com.alipay.pussycat.publish.model.ServiceEvent;
import com.alipay.pussycat.publish.model.SimpleServiceModel;
import com.alipay.pussycat.register.ServerRegisterService;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * 实现服务发布操作
 * @author recollects
 * @version V1.0
 *
 */
@Service
public class ServiceEventPublisherImpl extends AbstractServiceEventPublisher{

    @Autowired
    private ServerRegisterService serverRegisterService;

    @Override
    public void publish(SimpleServiceModel model) throws ServicePublishException{

        //使用方需要发服务的接口类型传过来,对接口里的方法,解析出来,注册到注册中心去

        logger_publish.info("测试日志打印");

        //接口信息
        Class clazz = model.getClazz();

        if (!clazz.isInterface()){
            logger_publish.warn("注册非interface类型,model:{}",model);
            throw new RuntimeException("服务非interface类型...");
        }

        Method[] methods = clazz.getMethods();

        //对方法数组解析

        List<Method> methodList = Arrays.asList(methods);

        Iterators.all(methodList.iterator(), new Predicate<Method>() {
            @Override
            public boolean apply(Method method) {

                Class<?>[] parameterTypes = method.getParameterTypes();

                Parameter[] parameters = method.getParameters();


                ServiceEvent event = new ServiceEvent();
                event.setHost(model.getHost());
                event.setPort(model.getPort());
                event.setMethodName(method.getName());
                event.setParameterTypes(parameterTypes);
                event.setInterfaceName(clazz.getName());
                serverRegisterService.register(event);

                return true;
            }
        });


    }
}
