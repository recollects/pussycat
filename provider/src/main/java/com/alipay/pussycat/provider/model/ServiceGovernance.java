package com.alipay.pussycat.provider.model;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 服务治理
 *
 * @author wb-smj330392
 * @create 2018-06-12 16:40
 */
public class ServiceGovernance {

    /**
     * 是否降级
     */
    private AtomicBoolean isDegrade = new AtomicBoolean(false);

    /**
     * 是否熔断
     */
    private AtomicBoolean isFuse = new AtomicBoolean(false);

    /**
     * 是否限流
     */
    private AtomicBoolean isStreamLimit = new AtomicBoolean(false);

    public AtomicBoolean getIsDegrade() {
        return isDegrade;
    }

    public void setIsDegrade(AtomicBoolean isDegrade) {
        this.isDegrade = isDegrade;
    }

    public AtomicBoolean getIsFuse() {
        return isFuse;
    }

    public void setIsFuse(AtomicBoolean isFuse) {
        this.isFuse = isFuse;
    }

    public AtomicBoolean getIsStreamLimit() {
        return isStreamLimit;
    }

    public void setIsStreamLimit(AtomicBoolean isStreamLimit) {
        this.isStreamLimit = isStreamLimit;
    }
}
