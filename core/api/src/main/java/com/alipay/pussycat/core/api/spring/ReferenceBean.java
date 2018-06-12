package com.alipay.pussycat.core.api.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author recollects
 * @date 2018年06月11日 下午7:46 
 * @version V1.0
 *
 */
public class ReferenceBean implements  ApplicationContextAware, InitializingBean, DisposableBean {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
