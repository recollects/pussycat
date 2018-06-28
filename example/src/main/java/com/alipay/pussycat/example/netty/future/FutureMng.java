package com.alipay.pussycat.example.netty.future;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author recollects
 * @date 2018年06月28日 下午4:58 
 * @version V1.0
 *
 */
public class FutureMng {

    ConcurrentMap<String, SyncFuture> invokerFutureMaps = Maps.newConcurrentMap();

    public void addInvokerFuture(String id, SyncFuture future) {
        invokerFutureMaps.putIfAbsent(id, future);
    }

    public SyncFuture removeInvokerFuture(String id, SyncFuture future) {
        return invokerFutureMaps.remove(id);
    }

    public SyncFuture getInvokerFuture(String id) {
        return invokerFutureMaps.get(id);
    }

}
