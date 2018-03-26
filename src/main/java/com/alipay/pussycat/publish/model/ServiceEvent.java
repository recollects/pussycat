package com.alipay.pussycat.publish.model;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;

/**
 * 网络传输包涵接口信息
 *
 * Created by recollects on 18/3/12.
 */
public abstract class ServiceEvent implements Serializable {

    private static final long serialVersionUID = 3387043415446548892L;

    /**
     * ip地址
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数类型[重载问题]
     */
    private  Class<?>[] parameterTypes;

    /**
     * 参数唯一串
     */
    private String parameters;

    /**
     * 入参
     */
    private Object[] inputParameters;

    /**
     * 时间戳
     */
    private final long timestamp;

    public ServiceEvent() {
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * 防止服务重服注册[每台机器,一个端口下只能注册一个服务(重载属于多个服务)]
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("rpc://");
        sb.append(getHost()).append(":");
        sb.append(getPort()).append("/");
        sb.append(getInterfaceName()).append("/");
        sb.append(getMethodName()).append("#");
        sb.append(getParameters());
        return StringUtils.equals(obj.toString(),sb.toString());
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Object[] getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(Object[] inputParameters) {
        this.inputParameters = inputParameters;
    }
}
