package com.alipay.pussycat.consumer;

import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.ServiceMetadata;
import com.alipay.pussycat.core.common.register.conf.ConsumerConfig;

/**
 * @author wb-smj330392
 * @create 2018-04-28 13:49
 */
public interface ServiceConsume<T> {

    /**
     * 生成服务代理进行对象调用
     * @param metadata
     * @return
     * @throws Exception
     */
    T consume(ServiceMetadata metadata) throws Exception;

    /**
     *
     * @param config
     * @return
     * @throws PussycatException
     */
    T consume(ConsumerConfig config)throws PussycatException;
}