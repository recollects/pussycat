package com.alipay.pussycat.example.netty.future;

import java.util.concurrent.*;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午6:50 
 * @version V1.0
 *
 */
public class SyncFuture<T> implements Future<T> {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private T
                           response;

    public SyncFuture() {
    }

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
        if (response != null) {
            return true;
        }
        return false;
    }

    @Override
    public T get() throws InterruptedException {
        countDownLatch.await();
        return this.response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException {
        if (countDownLatch.await(timeout, unit)) {
            return this.response;
        }
        return null;
    }

    public void setResponse(T response) {
        this.response = response;
        countDownLatch.countDown();
    }

}
