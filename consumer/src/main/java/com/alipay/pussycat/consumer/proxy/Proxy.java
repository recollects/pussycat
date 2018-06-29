package com.alipay.pussycat.consumer.proxy;

/**
 *
 * @author recollects
 * @date 2018年06月29日 下午3:35 
 * @version V1.0
 *
 */
public interface Proxy {

    <T> T getProxy(Class<T> clazz);
}
