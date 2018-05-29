package com.alipay.pussycat.consumer;

import com.alipay.pussycat.server.model.ServiceMetadata;

/**
 * @author wb-smj330392
 * @create 2018-04-28 13:49
 */
public interface ServiceConsume {

    /**
     * 生成服务代理进行对象调用
     * @param metadata
     * @return
     * @throws Exception
     */
    Object consume(ServiceMetadata metadata) throws Exception;
}