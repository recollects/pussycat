package com.alipay.pussycat.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *  应用上下文获取
 *
 * Created by recollects on 18/3/12.
 */
public class PussyCatApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public static  <T> T getBean(String beanName){
        return (T)applicationContext.getBean(beanName);
    }

    /**
     *
     * @param beanType
     * @param <T>
     * @return
     */
    public static  <T> T getBean(Class<T> beanType){
        return (T)applicationContext.getBean(beanType);
    }

}
