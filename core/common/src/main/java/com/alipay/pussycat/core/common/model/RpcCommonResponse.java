package com.alipay.pussycat.core.common.model;

/**
 *
 * @author recollects
 * @date 2018年06月28日 下午4:00 
 * @version V1.0
 *
 */
public class RpcCommonResponse extends RemotingTransporter{

    /**
     * 通讯是否成功标识
     */
    private boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
