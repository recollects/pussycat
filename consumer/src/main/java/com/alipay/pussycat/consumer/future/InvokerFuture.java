package com.alipay.pussycat.consumer.future;

import com.alipay.pussycat.core.common.model.RpcCommonResponse;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author recollects
 * @date 2018年06月28日 下午3:52 
 * @version V1.0
 *
 */
public interface InvokerFuture<T extends RpcCommonResponse> {

    T waitResponse() throws InterruptedException;

    T waitResponse(long timeout, TimeUnit unit) throws InterruptedException;

    void putResponse(T response);

    T createFailResponse();

    boolean isDone();

    Integer id();
}
