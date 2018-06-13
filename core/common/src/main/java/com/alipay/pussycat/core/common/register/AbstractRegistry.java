package com.alipay.pussycat.core.common.register;

import com.alipay.pussycat.core.common.register.conf.ConsumerConfig;
import com.alipay.pussycat.core.common.register.conf.ProviderConfig;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 *
 * @author recollects
 * @date 2018年06月13日 上午9:55 
 * @version V1.0
 *
 */
public abstract class AbstractRegistry implements Registry {

    /**
     * 本地缓存
     */
    protected static Map<String,ProviderConfig> providerLocalMap = Maps.newConcurrentMap();
    protected static Map<String,ConsumerConfig> consumerLocalMap = Maps.newConcurrentMap();



}
