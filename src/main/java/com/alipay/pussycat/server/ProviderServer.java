/**
 * High-Speed Service Framework (HSF)
 * 
 * www.taobao.com
 * 	(C) �Ա�(�й�) 2003-2014
 */
package com.alipay.pussycat.server;

import com.alipay.pussycat.server.model.ServiceMetadata;

/**
 * 描述：作为HSF提供者的server服务接口
 *server端做的主要两件事：注册服务、启动服务
 */
public interface ProviderServer {

    /**
     * 启动服务
     * @throws Exception
     */
    void startPYCServer() throws Exception;

    /**
     * 向注册中心注册服务
     * @throws Exception
     */
    void registerPYCServer(ServiceMetadata metadata) throws Exception;

    /**
     * 向监控中心注册服务
     * 以后实现
     * @throws Exception
     */
    void MonitorPYCServer() throws Exception;

    void stopPYCServer() throws Exception;


}
