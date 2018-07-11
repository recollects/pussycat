package com.alipay.pussycat.core.common.model;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.alipay.pussycat.core.common.utils.ToStringUtil;

/**
 * @author wb-smj330392
 * @create 2018-05-26 18:47
 */
public class PussycatResponse {

    // 远程端返回的结果集
    private volatile RemotingTransporter remotingTransporter;
    /**
     * 通讯是否成功标识
     */
    private boolean success;

    // 请求的requestId;
    private final long requestId;

    // 请求的默认超时时间
    private final long timeoutMillis;


    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public PussycatResponse(long requestId, long timeoutMillis) {
        this.requestId = requestId;
        this.timeoutMillis = timeoutMillis;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    @Override
    public String toString() {
        return ToStringUtil.defaultStyle(this);
    }

    public RemotingTransporter getRemotingTransporter() {
        return remotingTransporter;
    }

    public void setRemotingTransporter(RemotingTransporter remotingTransporter) {
        this.remotingTransporter = remotingTransporter;
    }

    public long getRequestId() {
        return requestId;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    /**
     * 异步请求等待返回结果
     * @return
     * @throws InterruptedException
     */
    public RemotingTransporter waitResponse() throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.remotingTransporter;
    }

    /**
     * 异步请求将结果返回
     * @param remotingTransporter
     */
    public void putResponse(final RemotingTransporter remotingTransporter){
        this.remotingTransporter = remotingTransporter;
        //接收到对应的消息之后需要countDown
        this.countDownLatch.countDown();
    }
}
