package com.alipay.pussycat.core.common.register.conf;

import java.io.Serializable;

/**
 *
 * @author recollects
 * @date 2018年06月29日 上午11:14 
 * @version V1.0
 *
 */
public class ServerConfig implements Serializable {
    private static final long serialVersionUID = 6186806107733810833L;

    private String                          host;

    private int                             port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
