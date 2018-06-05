package com.alipay.pussycat.spring.support;

import com.alipay.pussycat.common.exception.PussycatException;
import com.alipay.pussycat.common.exception.PussycatExceptionEnum;
import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.model.ServiceMetadata;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.PussycatProviderContainer;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.common.utils.SystemUtils;
import com.alipay.pussycat.server.ProviderServer;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 提供spring 来提供服务
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:53
 */
public class PussycatServerBean implements InitializingBean, ApplicationListener, DisposableBean {

    private static final Logger logger_publish = LogDef.SERVICE_PUBLISH_DIGEST;

    private int timeout = PussycatContants.DEFAULT_TIMEOUT;

    private String version = PussycatContants.DEFAULT_VERSION;

    private Object target;

    private String serviceName;

    private final ServiceMetadata metadata = new ServiceMetadata();

    private ProviderServer providerServer = PussycatServiceContainer.getInstance(ProviderServer.class);

    /**
     *
     */
    private void check() {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(serviceName);
        Preconditions.checkArgument(timeout > 0, "超时时间不能小于0");

        try {
            Class<?> serviceItf = Class.forName(serviceName);
            if (!serviceItf.isInterface()) {
                throw new PussycatException(PussycatExceptionEnum.E_10004);
            }
            if (!serviceItf.isAssignableFrom(target.getClass())) {
                throw new PussycatException(PussycatExceptionEnum.E_10005);
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        check();
        //开启pussycat服务实例
        if (!providerServer.isStarted()) {
            try {
                providerServer.start();
            } catch (Exception e) {
                logger_publish.info("start pussycat server fail......");
                System.out.println("启动异常:" + e);
            }
        }

    }

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

    public void setVersion(String version) {
        this.version = version;
        metadata.setVersion(version);
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

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            //            //向注册中心注册服务
            try {
                metadata.setHost(SystemUtils.getIP());
                providerServer.register(metadata);
                PussycatProviderContainer.providerObject.put(serviceName, target);
            } catch (Exception e) {
                logger_publish.info("Pussycat 注册服务失败..." + metadata.getInterfaceName());
                System.out.println("onApplicationEvent register异常:" + e);
            }
            //开启pussycat服务实例
            try {
                if (providerServer.isStarted()) {
                    //TODO
                    //                    providerServer.stop();
                    //                    providerServer.start();
                } else {
                    providerServer.start();
                }
            } catch (PussycatException e) {
                logger_publish.info("Pussycat 服务refreshed异常..." + metadata.getInterfaceName());
                System.out.println("onApplicationEvent start异常:" + e);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        try {
            providerServer.stop();
        } catch (PussycatException e) {
            logger_publish.error("停止服务异常", e);
            e.printStackTrace();
        }
    }
}
