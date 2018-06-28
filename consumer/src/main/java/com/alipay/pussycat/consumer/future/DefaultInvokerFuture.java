package com.alipay.pussycat.consumer.future;

import com.alipay.pussycat.core.common.model.RpcCommonResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author recollects
 * @date 2018年06月28日 下午3:56 
 * @version V1.0
 *
 */
public class DefaultInvokerFuture<T extends RpcCommonResponse> implements InvokerFuture<T> {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private T response;

    @Override
    public T waitResponse() throws InterruptedException {
        this.countDownLatch.await();
        return this.response;
    }

    @Override
    public T waitResponse(long timeout, TimeUnit unit) throws InterruptedException {
        this.countDownLatch.await(timeout, unit);
        return this.response;
    }

    @Override
    public void putResponse(T response) {
        this.response = response;
        this.countDownLatch.countDown();
    }

    @Override
    public T createFailResponse() {
        RpcCommonResponse response = new RpcCommonResponse();
        //TODO code改成short类型,返回500,byte不够用
        response.setCode((byte) 0);
        response.setTimestamp(System.currentTimeMillis());

        return (T) response;
    }

    @Override
    public boolean isDone() {
        return this.countDownLatch.getCount() <= 0;
    }

    @Override
    public Integer id() {
        //TODO 请求ID统一用integer,integer够用了
        return Integer.parseInt(response.getRequestId()+"");
    }
}
