package com.alipay.pussycat.core.api.spring;

import com.alipay.pussycat.core.api.annotation.Service;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author recollects
 * @date 2018年06月11日 下午7:47 
 * @version V1.0
 *
 */
public class ServiceBean implements BeanPostProcessor, InitializingBean, DisposableBean, ApplicationContextAware {

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


        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();

        Service service = clazz.getAnnotation(Service.class);
        if (service!=null){
            //TODO 处理
            //1.根据配置选择注册到loacl-registry还是redis-registry
            //2.装接口配置信息存储本地缓存
            //
        }

        return bean;
    }
}
