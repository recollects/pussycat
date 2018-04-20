/**
 * High-Speed Service Framework (HSF)
 * 
 * www.taobao.com
 * 	(C) �Ա�(�й�) 2003-2014
 */
package com.alipay.pussycat.server;

/**
 * 描述：作为HSF提供者的server服务接口
 * 
 * @author <a href="mailto:bixuan@taobao.com">bixuan</a>
 * @author kongming.lrq
 * 
 */
public interface ProviderServer {



    void startPYCServer() throws Exception;



    void stopPYCServer() throws Exception;


    boolean isStarted();
}
