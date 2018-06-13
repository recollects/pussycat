package com.alipay.pussycat.example.netty.future;

import java.util.concurrent.*;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午6:50 
 * @version V1.0
 *
 */
public class SyncFuture<T> implements Future<T>{

    private T response;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public T get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
