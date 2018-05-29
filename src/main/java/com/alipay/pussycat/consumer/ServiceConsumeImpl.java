package com.alipay.pussycat.consumer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.alipay.pussycat.common.model.ApplicationModel;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.consumer.model.PussycatRequest;
import com.alipay.pussycat.consumer.model.SimpleServiceConsumerModel;
import com.alipay.pussycat.register.ServerRegisterService;
import com.alipay.pussycat.register.redis.impl.ServerRegisterServiceImpl;
import com.alipay.pussycat.server.model.ProviderMethodModel;
import com.alipay.pussycat.server.model.ServiceMetadata;
import com.alipay.pussycat.server.model.SimpleServiceProviderModel;

/**
 * @author wb-smj330392
 * @create 2018-04-28 13:53
 */
public class ServiceConsumeImpl implements ServiceConsume{

    private static AtomicLong reqId = new AtomicLong(1000);



    @Override
    public Object consume(ServiceMetadata metadata) throws Exception {
        //先从本地缓存中获取
        SimpleServiceConsumerModel consumedServiceModel = ApplicationModel.instance().getConsumedServiceModel(metadata.getInterfaceName());
        if (consumedServiceModel !=null ){
            return consumedServiceModel.getProxyObject();
        }
        //生成服务代理
        if(metadata.getItfClass() != null){
            List<Class<?>> interfaces = new ArrayList<Class<?>>();
            interfaces.add(metadata.getItfClass());
            PYCServiceProxy pycServiceProxy = new PYCServiceProxy(metadata, (Class[])interfaces.toArray());

        }

        return null;
    }

    /**
     * 生成服务代理对象
     */
    public static class PYCServiceProxy implements InvocationHandler{
        private final ServiceMetadata serviceConsumerMetadata;
        private final SimpleServiceConsumerModel serviceModel;
        private final Object instance;

        public PYCServiceProxy(ServiceMetadata metadata,Class[] clazz) {
            this.serviceConsumerMetadata = metadata;
            this.instance = Proxy.newProxyInstance(metadata.getItfClass().getClassLoader(),clazz,this);
            ApplicationModel.instance().initConsumerService(metadata.getInterfaceName(),new SimpleServiceConsumerModel(metadata,this.instance,true));
            serviceModel = ApplicationModel.instance().getConsumedServiceModel(metadata.getInterfaceName());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SimpleServiceProviderModel providedServiceModel = ApplicationModel.instance().getProvidedServiceModel(
                this.serviceConsumerMetadata.getInterfaceName());
            Collection<ProviderMethodModel> methodModels = providedServiceModel.getProviderMethodModels().values();
            PussycatRequest pussycatRequest = null;
            for (ProviderMethodModel providerMethodModel : methodModels){
                if (providedServiceModel.equals(method)){
                    pussycatRequest = new PussycatRequest(reqId.incrementAndGet(),
                        providedServiceModel.getTimeout(),
                        providedServiceModel.getServiceName(),
                        providerMethodModel.getMethodName(),
                        args,
                        providerMethodModel.getMethod().getParameterTypes());
                }
            }
            ServerRegisterService serverRegisterService = PussycatServiceContainer.getInstance(ServerRegisterService.class);
            ((ServerRegisterServiceImpl)serverRegisterService).call(pussycatRequest,providedServiceModel);

        }
    }
}
