package com.alipay.pussycat.server.model;

import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.StringUtils;
import com.alipay.pussycat.common.utils.ToStringUtil;
import org.slf4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 网络传输包涵接口信息
 *
 * Created by recollects on 18/3/12.
 */
public class ServiceMetadata implements Serializable {

    protected static final Logger logger_metadata = LogDef.SERVICE_METADATA_DIGEST;

    private static final long serialVersionUID = 3387043415446548892L;
    public static final String DEFAULT_VERSION = "1.0.0"; // 默认版本号
    public static final int DEFAULT_TIMEOUT = 3000; // 默认超时时间

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

    private Class<?> ItfClass;

    /**
     * 服务类
     */
    private Object target;

    /**
     * 方法名
     */
    private String methodName;
    private String[] methodNames;

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

    private int timeout = DEFAULT_TIMEOUT;
    private String version = DEFAULT_VERSION;

    /**
     * 代理类型
     */
    private String proxyStyle = "jdk";

    private String uniqueName;

    public ServiceMetadata() {
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
        sb.append(StringUtils.parameterTypesToStr(getParameterTypes()));
        return StringUtils.equals(obj.toString(),sb.toString());
    }


    public String getMethodStamp(){
        StringBuilder methodSB = new StringBuilder();
        Method[] methods = target.getClass().getMethods();
        for (Method method: methods) {
            String name = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            StringBuilder parameterSB = new StringBuilder();
            for (Class<?> parameterType : parameterTypes ){
                parameterSB.append(parameterType.getName()).append("-");
            }
            methodSB.append(name).append(":").append(parameterSB);
        }
        return methodSB.toString();
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
        this.interfaceName = interfaceName.trim();
        try {
            setItfClass(Class.forName(this.interfaceName));
        } catch (ClassNotFoundException e) {
            logger_metadata.info("接口类不存在");
        }
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

    public Class<?> getItfClass() {
        return ItfClass;
    }

    public void setItfClass(Class<?> itfClass) {
        ItfClass = itfClass;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getUniqueName() {
        return interfaceName+"#"+version;
    }
}
