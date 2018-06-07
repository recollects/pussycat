package com.alipay.pussycat.netty.model;

import java.io.Serializable;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午12:34
 */
public class NettyResponse implements Serializable {

    private String requestId;

    private static final long serialVersionUID = -7203219966957193986L;
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
}
