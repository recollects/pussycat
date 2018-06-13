package com.alipay.pussycat.core.common.register.conf;

import java.io.Serializable;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午7:25 
 * @version V1.0
 *
 */
public class MethodConfig implements Serializable{

    private String  name;
    private Integer timeout;

    //參數
    private Object[]   reqArgs;
    //参数类型
    private Class<?>[] argTypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Object[] getReqArgs() {
        return reqArgs;
    }

    public void setReqArgs(Object[] reqArgs) {
        this.reqArgs = reqArgs;
    }

    public Class<?>[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(Class<?>[] argTypes) {
        this.argTypes = argTypes;
    }
}
