package com.alipay.pussycat.example.netty.result;

import com.alipay.pussycat.example.netty.model.NettyResponse;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午5:52
 */
public class RpcContextResult {

    private static Map<String, NettyResponse> contextResponse = Maps.newConcurrentMap();

    public static Map<String, NettyResponse> getContextResponse() {
        return contextResponse;
    }

    public static void setContextResponse(Map<String, NettyResponse> contextResponse) {
        RpcContextResult.contextResponse = contextResponse;
    }
}
