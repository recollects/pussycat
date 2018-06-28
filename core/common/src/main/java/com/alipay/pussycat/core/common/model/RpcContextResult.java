package com.alipay.pussycat.core.common.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午6:29
 */
@Deprecated
public class RpcContextResult {

    private static Map<Long, PussycatResponse> resultMap = Maps.newConcurrentMap();

    public static Map<Long, PussycatResponse> getResultMap() {
        return resultMap;
    }
}
