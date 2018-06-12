package com.alipay.pussycat.core.common.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年06月03日 下午3:35
 */
public class PussycatProviderRefCache {

    public static Map<String, Object> providerObject = Maps.newConcurrentMap();

}
