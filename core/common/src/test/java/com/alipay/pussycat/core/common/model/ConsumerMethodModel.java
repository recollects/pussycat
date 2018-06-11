package com.alipay.pussycat.core.common.model;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import com.alipay.pussycat.common.utils.StringUtils;

/**
 * @author wb-smj330392
 * @create 2018-05-03 16:30
 *
 * ConsumerMethodModel和ProviderMethodModel一样，后期考虑两种类型方法模型的差别
 */
public class ConsumerMethodModel {

    private final static AtomicInteger INDEX_GENERATOR = new AtomicInteger(1);
    private transient final Method method;
    private final           String methodName;
    private final           String methodArgTypesJoiner;

    private String fullMethodInfoToExport;

    public String getFullMethodInfoToExport() {
        return fullMethodInfoToExport;
    }

    private final String methodStamp;
    private final String serviceName;
    private transient final int index = INDEX_GENERATOR.getAndIncrement();
    private transient final int timeout;

    //private final String spasMethodName;

    public ConsumerMethodModel(Method method, String serviceName, int timeout) {
        this.method = method;
        this.serviceName = serviceName;
        this.methodName = method.getName();

        StringBuilder methodkeyBuilder = new StringBuilder(method.getName());
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            methodArgTypesJoiner = StringUtils.parameterTypesToStr(parameterTypes);

            methodkeyBuilder.append("~").append(methodArgTypesJoiner);

        } else {
            methodArgTypesJoiner = "";
        }

        this.methodStamp = methodkeyBuilder.toString();
        this.timeout = timeout;
        fullMethodInfoToExport = serviceName + "#" + methodStamp + "#" + timeout;

    }

    public Method getMethod() {
        return method;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodKeyWithServiceName() {
        return serviceName + "#" + methodStamp;
    }

    public String getMethodKey() {
        return methodStamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getIndex() {
        return index;
    }

    //public String getSpasMethodName() {
    //    return spasMethodName;
    //}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + methodArgTypesJoiner.hashCode();
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConsumerMethodModel other = (ConsumerMethodModel) obj;
        if (methodName == null) {
            if (other.methodName != null)
                return false;
        } else if (!methodName.equals(other.methodName))
            return false;
        if (!methodArgTypesJoiner.equals(other.methodArgTypesJoiner))
            return false;
        return true;
    }

}
