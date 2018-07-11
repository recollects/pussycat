package com.alipay.pussycat.provider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alipay.pussycat.core.api.annotation.Service;
import com.alipay.pussycat.core.api.annotation.ServiceMethodController;
import com.alipay.pussycat.core.common.model.RemotingTransporter;
import com.alipay.pussycat.core.common.model.TransportProtocal;
import com.alipay.pussycat.core.common.netty.transport.NettyTransportClientController;
import com.alipay.pussycat.core.common.register.Registry;
import com.alipay.pussycat.core.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.core.common.utils.SystemUtils;
import com.alipay.pussycat.provider.model.MethodWrapper;
import com.alipay.pussycat.provider.model.RegisterServiceMeta;
import com.alipay.pussycat.provider.model.ServiceWrapper;
import com.alipay.pussycat.register.local.LocalRegistry;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.util.IPAddressUtil;

/**
 * @author wb-smj330392
 * @create 2018-06-13 11:11
 */
public class DefaultProvider {

    private static final Logger logger = LoggerFactory.getLogger(DefaultProvider.class);

    /**
     * 服务注册的地址  多注册中心
     */
    private String registerAddress = "127.0.0.1:13142,127.0.0.1:13143,127.0.0.1:13144";

    /**
     * 服务暴露的端口
     */
    private int exposePort = 9999;

    /**
     * 需要发布的服务
     */
    private Object[] target;

    public DefaultProvider() {
        initialize();
    }

    /**
     * 所有对外发布的服务
     */
    List<ServiceWrapper> serviceWrappers;

    /**
     * 所有向注册中心注册的服务集合
     */
    List<RemotingTransporter> registerService;

    /**
     * 备用List
     */
    private Map<String,ServiceWrapper> globalPublishService = Maps.newConcurrentMap();

    private ProviderServer providerServer = PussycatServiceContainer.getInstance(ProviderServer.class);
    private LocalRegistry localRegistry;
    private NettyTransportClientController nettyTransportClientController;


    private void initialize(){
        localRegistry = new LocalRegistry();
        nettyTransportClientController = new NettyTransportClientController();
    }

    /**
     * 编制服务
     */
    public DefaultProvider publishService(Object[] target){
        this.target = target;
        if (target != null && target.length > 0){
            List<ServiceWrapper> serviceWrappers = new ArrayList<>();
            for (Object obj : target){
                Class<?> itfClass = obj.getClass().getInterfaces()[0];
                ServiceWrapper serviceWrapper;
                //针对接口上的service注解
                Service serviceAnnotation = itfClass.getAnnotation(Service.class);
                if (serviceAnnotation != null){
                 String serviceName = StringUtils.isNotEmpty(serviceAnnotation.interfaceName())? itfClass.getName():serviceAnnotation.interfaceName();
                    boolean register = serviceAnnotation.register();
                    int timeout = serviceAnnotation.timeout();
                    String version = serviceAnnotation.version();
                    int weight = serviceAnnotation.weight();
                    String white = serviceAnnotation.white();
                    boolean async = serviceAnnotation.async();


                    //针对接口内的不同方法上的注解
                    Method[] methods = itfClass.getMethods();
                    List<MethodWrapper> methodWrappers = new ArrayList<>();
                    for (Method method : methods){
                        ServiceMethodController methodAnnotation = method.getAnnotation(ServiceMethodController.class);
                        String degradePath = methodAnnotation.degradePath();
                        int degradePercent = methodAnnotation.degradePercent();
                        boolean supportCallLimit = methodAnnotation.isSupportCallLimit();
                        boolean supportDegrade = methodAnnotation.isSupportDegrade();
                        int maxCallCount = methodAnnotation.maxCallCount();
                        MethodWrapper methodWrapper = new MethodWrapper(method.getName(), method.getParameterTypes(),supportDegrade, degradePath, supportCallLimit, maxCallCount, degradePercent);
                        methodWrappers.add(methodWrapper);
                    }

                    serviceWrapper = new ServiceWrapper(obj, serviceName, weight, register, white, timeout,version,async,methodWrappers);
                    serviceWrappers.add(serviceWrapper);
                }
            }
            this.serviceWrappers = serviceWrappers;
        }
        initGlobalPublishService(serviceWrappers);
        return this;
        //编制服务
    }

    /**
     * 开启网络的连接：和注册中心、消费端和服务端
     */
    public void start(){
        try {
            //1 如果有注册服务  注册服务的相关业务
            initRegisterServiceList(serviceWrappers);
            //2 作为客户端向注册中心进行注册服务
            nettyTransportClientController.nettyHandlerReady();
            //TODO 未完待续
            localRegistry.registerAndStart(this);
            //3 作为服务提供端等待消费端的连接
            providerServer.serverStart();
        }catch (Exception e){

        }


    }


    private void initGlobalPublishService(List<ServiceWrapper> serviceWrappers){
        for (ServiceWrapper serviceWrapper : serviceWrappers){
            this.globalPublishService.put(serviceWrapper.getServiceName(),serviceWrapper);
        }
    }


    private void initRegisterServiceList(List<ServiceWrapper> serviceWrappers){
        for (ServiceWrapper serviceWrapper : serviceWrappers){
            if (serviceWrapper.getIsSupportRegister()){
                RegisterServiceMeta registerServiceMeta = new RegisterServiceMeta(serviceWrapper);
                registerServiceMeta.setHost(SystemUtils.getIP());
                registerServiceMeta.setPort(exposePort);
                RemotingTransporter registerRequestTransporter = RemotingTransporter.createRequestTransporter(TransportProtocal.REGISTER_REQUEST_CODE, registerServiceMeta);
                this.registerService.add(registerRequestTransporter);
            }
        }
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public int getExposePort() {
        return exposePort;
    }

    public void setExposePort(int exposePort) {
        this.exposePort = exposePort;
    }

    public Object[] getTarget() {
        return target;
    }

    public void setTarget(Object[] target) {
        this.target = target;
    }

    public List<ServiceWrapper> getServiceWrappers() {
        return serviceWrappers;
    }

    public void setServiceWrappers(List<ServiceWrapper> serviceWrappers) {
        this.serviceWrappers = serviceWrappers;
    }

    public List<RemotingTransporter> getRegisterService() {
        return registerService;
    }

    public void setRegisterService(List<RemotingTransporter> registerService) {
        this.registerService = registerService;
    }

    public Map<String, ServiceWrapper> getGlobalPublishService() {
        return globalPublishService;
    }

    public void setGlobalPublishService(Map<String, ServiceWrapper> globalPublishService) {
        this.globalPublishService = globalPublishService;
    }
}
