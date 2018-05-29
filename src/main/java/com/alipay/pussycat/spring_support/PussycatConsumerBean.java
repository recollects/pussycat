package com.alipay.pussycat.spring_support;

import java.util.concurrent.atomic.AtomicBoolean;

import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.consumer.ServiceConsume;
import com.alipay.pussycat.server.model.ServiceMetadata;
import com.alipay.pussycat.register.ServerRegisterService;
import org.slf4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月07日 上午10:47
 */
public class PussycatConsumerBean implements InitializingBean,FactoryBean {

    protected static final Logger logger_consume = LogDef.SERVICE_CONSUME_DIGEST;

    private ServiceConsume serviceConsume = PussycatServiceContainer.getInstance(ServiceConsume.class);
    private ServerRegisterService registerService = PussycatServiceContainer.getInstance(ServerRegisterService.class);

    private static final int DEFAULT_TIMEOUT = 3000;
    private static final String DEFAULT_VERSION = "1.0";

    private int timeout = DEFAULT_TIMEOUT;

    private String version = DEFAULT_VERSION;

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

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
        metadata.setInterfaceName(serviceInterface);
    }

    private String serviceInterface;

    private final ServiceMetadata metadata = new ServiceMetadata();

    static private AtomicBoolean isProviderStarted = new AtomicBoolean(false);

    private Object serviceProxy;

    private Object objMonitor = new Object();



    @Override
    public void afterPropertiesSet() throws Exception {
        //TODO 创建服务.
        // 初始化判断
        check();
        // 获取服务实例 本地缓存获取+生成服务代理
        Object obj = serviceConsume.consume(metadata);
        metadata.setTarget(obj);
        //订阅服务
        registerService.subscribe(metadata);
    }

    private void check() {
        Assert.notNull(metadata.getItfClass(),"接口类不存在");


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
        return metadata.getTarget();
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
