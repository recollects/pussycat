package com.alipay.pussycat.publish;

import com.alipay.pussycat.publish.model.ServiceEvent;

/**
 * 发布服务接口,入参这里先这样
 *
 *
 * Created by recollects on 18/3/12.
 */
public interface ServiceEventPublisher {

    /**
     * 服务发布接口操作
     *
     * @param clazz
     */
    void publishEvent(Class clazz);
}
