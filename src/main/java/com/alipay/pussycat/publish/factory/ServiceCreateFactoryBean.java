package com.alipay.pussycat.publish.factory;

import java.util.concurrent.atomic.AtomicBoolean;

import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.publish.model.ServiceMetadata;
import com.alipay.pussycat.register.ServerRegisterService;
import com.alipay.pussycat.server.ProviderServer;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 提供給客戶端发布服务用,一個生产bean的工厂
 * <p>到时候配置服务发布<p/>
 * FIXME 明瑾  这个类删了吧包括factory文件夹,直接用PussycatServiceExporter这个类!
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:53
 */
public class ServiceCreateFactoryBean implements InitializingBean, ApplicationContextAware,ApplicationListener {

    protected static final Logger logger_publish = LogDef.SERVICE_PUBLISH_DIGEST;

    private static final int DEFAULT_TIMEOUT = 3000;
    private static final String DEFAULT_VERSION = "1.0";

    private int timeout = DEFAULT_TIMEOUT;

    private String version = DEFAULT_VERSION;

    private Object target;

    private String serviceInterface;
    private final ServiceMetadata metadata = new ServiceMetadata();

    static private AtomicBoolean isProviderStarted = new AtomicBoolean(false);



    @Autowired
    private ProviderServer providerServer;

    /**
     *
     */
    private void check() {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(serviceInterface);
        Preconditions.checkArgument(timeout > 0, "超时时间不能小于0");

        try {
            Class<?> serviceItf = Class.forName(serviceInterface);
            if (!serviceItf.isInterface()){
                //TODO 异常以后版本中做深入处理
                throw new Exception("服务类型不是接口");
            }
            if (!serviceItf.isAssignableFrom(target.getClass())){
                throw new Exception("实际服务对象没有实现指定接口");
            }
        } catch (Exception e) {
        }

    }

    /**
     * TODO 处理逻辑需要提出去,还有创建完成发布服务也要单独提一个接口处理,
     * 尽量做到不同逻辑之间互相独立
     */
    private void publishService(){

        //TODO 注册服务
        ServerRegisterService serverRegisterService = PussycatServiceContainer.getInstance(ServerRegisterService.class);
        serverRegisterService.register(metadata);


    }



    @Override
    public void afterPropertiesSet() throws Exception {
        check();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

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

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
        metadata.setInterfaceName(serviceInterface);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextRefreshedEvent){
            publishService();
            //开启pussycat服务
            if (isProviderStarted.compareAndSet(false, true)){
                try {
                    providerServer.startPYCServer();
                } catch (Exception e) {
                    logger_publish.info("start pussycat server fail......");
                }
            }
        }
    }
}
