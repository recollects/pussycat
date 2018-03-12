package com.alipay.pussycat.publish;

import com.alipay.pussycat.publish.model.ServiceEvent;

/**
 * 发布服务接口,入参这里先这样
 *
 * TODO jiadong 先考虑下,看是否需要变动
 *
 * Created by recollects on 18/3/12.
 */
public interface ServiceEventPublisher {

    /**
     * 服务发布接口操作
     * TODO 这里设计需要再次考虑
     *
     * @param event
     */
    void publishEvent(ServiceEvent event);
}
