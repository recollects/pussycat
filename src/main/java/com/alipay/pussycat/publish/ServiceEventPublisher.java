package com.alipay.pussycat.publish;

import com.alipay.pussycat.publish.model.ServiceEvent;
import com.alipay.pussycat.publish.model.SimpleServiceModel;

/**
 * 发布服务接口
 *
 *
 * Created by recollects on 18/3/12.
 */
public interface ServiceEventPublisher {

    /**
     * 服务发布接口操作
     *
     * @param model
     */
    void publish(SimpleServiceModel model);



}
