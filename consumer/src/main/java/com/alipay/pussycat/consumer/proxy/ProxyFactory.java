package com.alipay.pussycat.consumer.proxy;

import com.alipay.pussycat.core.common.exception.PussycatException;

/**
 *
 * @author recollects
 * @date 2018年06月29日 下午3:34 
 * @version V1.0
 *
 */
public class ProxyFactory {

    /**
     *
     * @param proxyType
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newProxy(String proxyType,Class<T> clazz){

        if ("JDK".equals(proxyType)){
            Proxy proxy=new JDKProxy();
            return proxy.getProxy(clazz);
        }
        throw new PussycatException("","暂未提供。。。");
    }

}
