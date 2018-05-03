package com.alipay.pussycat.publish;

import com.alipay.pussycat.publish.exception.ServicePublishException;
import com.alipay.pussycat.publish.model.SimpleServiceProviderModel;

/**
 * 发布服务接口
 *
 *
 * Created by recollects on 18/3/12.
 */
public interface ServicePublisher {

    /**
     * 服务发布接口操作
     *
     * @param clazz
     */
    void publishService(Class clazz);

    void publish(SimpleServiceProviderModel model) throws ServicePublishException;



}
