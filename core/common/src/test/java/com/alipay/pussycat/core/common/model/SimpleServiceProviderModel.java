package com.alipay.pussycat.core.common.model;

import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.model.ServiceMetadata;
import com.alipay.pussycat.common.utils.SystemUtils;
import com.alipay.pussycat.common.utils.ToStringUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月29日 下午9:45
 */
public class SimpleServiceProviderModel implements Serializable {

    private static final long serialVersionUID = -2878450450752983225L;

    private final String host;

    private final Integer port = PussycatContants.DEFAULT_PORT;

    private final int timeout;

    private String version;

    private Class clazz;

    private final String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    //    public Object getServiceInstance() {
    //        return serviceInstance;
    //    }

    //    private final Object serviceInstance;

    //    private final ServiceMetadata metadata;

    public Map<String, ProviderMethodModel> getProviderMethodModels() {
        return providerMethodModels;
    }

    private final Map<String, ProviderMethodModel> providerMethodModels = new HashMap<String, ProviderMethodModel>();

    public SimpleServiceProviderModel(ServiceMetadata metada) {
        //        if (null == metada.getTarget()) {
        //            throw new IllegalArgumentException("服务[" + metada.getInterfaceName() + "]的Target为NULL.");
        //        }

        this.serviceName = metada.getInterfaceName();
        //        this.metadata = metada;
        //        this.serviceInstance = metada.getTarget();
        host = SystemUtils.getIP();
        timeout = metada.getTimeout();
        //        initMethod();

    }

    //    private void initMethod() {
    //        Method[] methodsToExport = null;
    //        methodsToExport  = null;//serviceInstance.getClass().getMethods();
    //        for (Method method : methodsToExport){
    //            method.setAccessible(true);
    //            ProviderMethodModel methodModel = providerMethodModels.get(method.getName());
    //            if (methodModel == null){
    //                //TODO 目前过期时间粗粒化到服务，后期可以到方法
    //                methodModel = new ProviderMethodModel(method,serviceName,this.timeout);
    //                providerMethodModels.put(method.getName(),methodModel);
    //            }
    //        }
    //    }

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

    //    public ServiceMetadata getMetadata() {
    //        return metadata;
    //    }

    @Override
    public String toString() {
        return ToStringUtil.defaultStyle(this);
    }
}
