package com.alipay.pussycat.serializable.model;

import java.io.Serializable;
import java.lang.reflect.Parameter;

/**
 * Created by recollects on 18/3/13.
 */
public class TransportModel implements Serializable{


    private static final long serialVersionUID = 7109955232132227839L;

    //返回结果
    private Object result;
    //方法名
    private String methodName;
    //参数
    private Class<?>[] parameterTypes;

    //接口名
    private String interfaceName;

    private String parameters;

    private Object[] inputParameters;

    public void setInputParameters(Object[] inputParameters) {
        this.inputParameters = inputParameters;
    }

    public Object[] getInputParameters() {
        return inputParameters;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getParameters() {
        return parameters;
    }
}
