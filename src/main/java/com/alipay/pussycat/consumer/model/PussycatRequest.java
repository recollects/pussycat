package com.alipay.pussycat.consumer.model;

import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.utils.ToStringUtil;
import com.alipay.pussycat.transport.model.RemotingTransporter;
import com.alipay.pussycat.transport.model.TransportBody;

/**
 * @author wb-smj330392
 * @create 2018-05-26 18:50
 */
public class PussycatRequest extends RemotingTransporter implements TransportBody {

    private String host;

    private Integer port = PussycatContants.DEFAULT_PORT;

    private final String     serviceName;
    private final String     methodName;
    //參數
    private final Object[]   reqArgs;
    //参数类型
    private final Class<?>[] argTypes;

    private int timeout = PussycatContants.DEFAULT_TIMEOUT;

    public PussycatRequest(int timeout, String serviceName, String methodName, Object[] reqArgs, Class<?>[] argTypes) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.reqArgs = reqArgs;
        this.argTypes = argTypes;
        this.timeout = timeout;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getReqArgs() {
        return reqArgs;
    }

    public Class<?>[] getArgTypes() {
        return argTypes;
    }

    public int getTimeout() {
        return timeout;
    }

    @Override
    public String toString() {
        return ToStringUtil.defaultStyle(this);
    }
}
