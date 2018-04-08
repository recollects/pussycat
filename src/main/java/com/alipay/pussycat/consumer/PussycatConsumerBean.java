package com.alipay.pussycat.consumer;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月07日 上午10:47
 */
public class PussycatConsumerBean implements InitializingBean {

    private Object serviceProxy;

    private Class<?> serviceInterface;

    private String version;

    @Override
    public void afterPropertiesSet() throws Exception {
        //TODO 创建服务.
        //
    }

    public Object getServiceProxy() {
        return serviceProxy;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
