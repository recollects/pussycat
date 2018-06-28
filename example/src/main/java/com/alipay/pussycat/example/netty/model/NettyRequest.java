package com.alipay.pussycat.example.netty.model;

import com.alipay.pussycat.core.common.utils.ToStringUtil;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午12:34
 */
public class NettyRequest implements Serializable {

    private String requestId;

    private static final long serialVersionUID = -174046094804097174L;

    String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public String toString() {
        return ToStringUtil.defaultStyle(this);
    }
}
