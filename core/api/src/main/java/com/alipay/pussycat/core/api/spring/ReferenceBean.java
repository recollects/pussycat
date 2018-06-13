package com.alipay.pussycat.core.api.spring;

import com.alipay.pussycat.core.api.annotation.Reference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author recollects
 * @date 2018年06月11日 下午7:46 
 * @version V1.0
 *
 */
public class ReferenceBean implements BeanPostProcessor, ApplicationContextAware, InitializingBean, DisposableBean {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //TODO 将注解服务注入到相应的服务里

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();

        Reference service = clazz.getAnnotation(Reference.class);
        if (service!=null){
            //TODO 处理
            //1.动态选择是选择loacl-registry还是redis-registry
            //2.根据配置需要校验的校验的,需要走配置化逻辑走配置化
            //3.装接口配置信息存储本地缓存
            //
        }

        return bean;
    }

}
