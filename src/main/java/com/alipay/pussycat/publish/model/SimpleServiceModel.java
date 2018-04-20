package com.alipay.pussycat.publish.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.pussycat.common.utils.SystemUtils;

/**
 *
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月29日 下午9:45
 */
public class SimpleServiceModel {

    private final String host;

    private final Integer port = 10550;

    private final int timeout ;

    private String version;

    private Class clazz;

    private final String serviceName;
    private final Object serviceInstance;
    private final ServiceMetadata metadata;
    private final Map<String, List<ProviderMethodModel>> providerMethodModels = new HashMap<String, List<ProviderMethodModel>>();


    public SimpleServiceModel(String serviceName, ServiceMetadata metada, Object serviceInstance) {
        if (null == serviceInstance) {
            throw new IllegalArgumentException("服务[" + serviceName + "]的Target为NULL.");
        }

        this.serviceName = serviceName;
        this.metadata = metada;
        this.serviceInstance = serviceInstance;
        host = SystemUtils.getIP();
        timeout = metadata.getTimeout();
        initMethod();

    }

    private void initMethod() {
        Method[] methodsToExport = null;
        methodsToExport  = serviceInstance.getClass().getMethods();
        for (Method method : methodsToExport){
            method.setAccessible(true);
            List<ProviderMethodModel> methodModels = providerMethodModels.get(method.getName());
            if (methodModels == null){
                methodModels = new ArrayList<>();
                //TODO 目前过期时间粗粒化到服务，后期可以到方法
                methodModels.add(new ProviderMethodModel(method,serviceName,this.timeout));
                providerMethodModels.put(method.getName(),methodModels);
            }
        }
    }


    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Class getClazz() {
        return clazz;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getVersion() {
        return version;
    }
}
