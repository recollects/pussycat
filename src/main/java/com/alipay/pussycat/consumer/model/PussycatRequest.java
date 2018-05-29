package com.alipay.pussycat.consumer.model;

import com.alipay.pussycat.transport.model.TransportBody;

/**
 * @author wb-smj330392
 * @create 2018-05-26 18:50
 */
public class PussycatRequest implements TransportBody {

    public String getTargetInterfaceName() {
        return targetInterfaceName;
    }

    public String getInvokeMethodName() {
        return invokeMethodName;
    }

    public Class<?>[] getArgTypes() {
        return argTypes;
    }


    public int getTimeout() {
        return timeout;
    }

    public Object[] getReqArgs() {
        return reqArgs;
    }

    private final String targetInterfaceName;
    private final String invokeMethodName;
    private final Object[] reqArgs;
    private final Class<?>[] argTypes;

    private final int timeout;

    public PussycatRequest(long id, int timeout, String targetInterfaceName, String methodName, Object[] reqArgs,Class<?>[] argTypes) {
        this.targetInterfaceName = targetInterfaceName;
        this.invokeMethodName = methodName;
        this.reqArgs = reqArgs;
        this.argTypes = argTypes;
        this.timeout = timeout;
    }


}
