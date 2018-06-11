package com.alipay.pussycat.provider;

import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.ServiceMetadata;

/**
 * 服务提供方
 */
public interface ProviderServer {

    /**
     *
     * @return
     */
    boolean isStarted();

    /**
     * 启动服务
     * @throws Exception
     */
    void start() throws PussycatException;

    /**
     * 向注册中心注册服务
     * @throws Exception
     */
    void register(ServiceMetadata metadata) throws PussycatException;

    /**
     * 向监控中心注册服务
     * 以后实现
     * @throws Exception
     */
    void monitor() throws PussycatException;

    /**
     *
     * @throws Exception
     */
    void stop() throws PussycatException;

}
