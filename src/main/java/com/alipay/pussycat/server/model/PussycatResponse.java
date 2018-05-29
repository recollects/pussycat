package com.alipay.pussycat.server.model;

import com.alipay.pussycat.transport.model.TransportBody;

/**
 * @author wb-smj330392
 * @create 2018-05-26 18:47
 */
public class PussycatResponse implements TransportBody {

    /**
     * 通讯是否成功标识
     */
    private boolean success;

    /**
     * 接口调用返回结果
     */
    private Object result;

    /**
     * 网络请求id
     */
    private long requestId;

    public void setResult(Object result) {
        this.result = result;
    }




    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Object getResult() {
        return result;
    }

}
