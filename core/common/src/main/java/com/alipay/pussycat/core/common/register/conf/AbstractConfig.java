package com.alipay.pussycat.core.common.register.conf;

import java.io.Serializable;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午7:27 
 * @version V1.0
 *
 */
public abstract class AbstractConfig implements Serializable {

    protected String appName;

    protected String id;

    private String protocol;

    protected Integer timeout;
    protected Integer connectTimeout;
    protected String  version;

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    protected abstract String getId();

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}


