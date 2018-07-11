package com.alipay.pussycat.provider.model;

import com.alipay.pussycat.core.common.model.TransportBody;

/**
 * @author wb-smj330392
 * @create 2018-07-02 17:04
 */
public class RegisterServiceMeta implements TransportBody {

   private ServiceWrapper serviceWrapper;

   //服务地址
   private String host;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    //服务端口
   private int port;

    public RegisterServiceMeta(ServiceWrapper serviceWrapper) {
        this.serviceWrapper = serviceWrapper;
    }

    public ServiceWrapper getServiceWrapper() {
        return serviceWrapper;
    }

    public void setServiceWrapper(ServiceWrapper serviceWrapper) {
        this.serviceWrapper = serviceWrapper;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


}
