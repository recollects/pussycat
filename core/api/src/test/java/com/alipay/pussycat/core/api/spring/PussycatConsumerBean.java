package com.alipay.pussycat.core.api.spring;

import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.consumer.ServiceConsume;
import com.alipay.pussycat.register.ServerRegisterService;
import com.alipay.pussycat.common.model.ServiceMetadata;
import com.alipay.pussycat.server.model.SimpleServiceProviderModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月07日 上午10:47
 */
public class PussycatConsumerBean implements InitializingBean, FactoryBean {

    private static final Logger logger_consume = LogDef.SERVICE_CONSUME_DIGEST;

    private ServiceConsume        serviceConsume  = PussycatServiceContainer.getInstance(ServiceConsume.class);
    private ServerRegisterService registerService = PussycatServiceContainer.getInstance(ServerRegisterService.class);

    private int timeout = PussycatContants.DEFAULT_TIMEOUT;

    private String version = PussycatContants.DEFAULT_VERSION;

    private Object target;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
        metadata.setTimeout(timeout);
    }

    public String getVersion() {
        return version;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
        metadata.setTarget(target);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        metadata.setInterfaceName(serviceName);
    }

    private String serviceName;

    private final ServiceMetadata metadata = new ServiceMetadata();

    private Object serviceProxy;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化判断
        check();

        //订阅服务
        SimpleServiceProviderModel providerModel = registerService.subscribe(metadata);

        if (providerModel != null) {
            System.out.println("接口订阅成功:" + metadata.getUniqueName());
        } else {
            System.out.println("接口订阅失败:" + metadata.getUniqueName());
        }

        metadata.setHost(providerModel.getHost());
        metadata.setPort(providerModel.getPort());
        metadata.setVersion(providerModel.getVersion());

        // 获取服务实例 本地缓存获取+生成服务代理
        Object obj = serviceConsume.consume(metadata);
        System.out.println("生成代理对象成功:" + obj);
        metadata.setTarget(obj);
        this.serviceProxy = obj;

    }

    private void check() {
        Assert.notNull(metadata.getItfClass(), "接口类不存在");
    }

    public Object getServiceProxy() {
        return serviceProxy;
    }

    public void setVersion(String version) {
        this.version = version;
        metadata.setVersion(version);
    }

    @Override
    public Object getObject() throws Exception {
        return serviceProxy;
    }

    @Override
    public Class<?> getObjectType() {
        if (metadata.getInterfaceName() == null) {
            return null;
        }

        if (null == metadata.getItfClass()) {
            return null;
        } else {
            return metadata.getItfClass();
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
