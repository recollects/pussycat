package com.alipay.pussycat.server.model;

import com.alipay.pussycat.common.utils.ToStringUtil;
import com.alipay.pussycat.transport.model.RemotingTransporter;
import com.alipay.pussycat.transport.model.TransportBody;

/**
 * @author wb-smj330392
 * @create 2018-05-26 18:47
 */
public class PussycatResponse extends RemotingTransporter implements TransportBody {

    /**
     * 通讯是否成功标识
     */
    private boolean success;

    /**
     * 接口调用返回结果
     */
    private Object result;

    public void setResult(Object result) {
        this.result = result;
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

    @Override
    public String toString() {
        return ToStringUtil.defaultStyle(this);
    }
}
