package com.alipay.pussycat.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.publish.model.ProviderMethodModel;
import com.alipay.pussycat.publish.model.SimpleServiceProviderModel;
import org.slf4j.Logger;

/**
 * @author wb-smj330392
 * @create 2018-04-29 18:49
 * 
 */
public class ApplicationModel {

    protected static final Logger logger_consume = LogDef.SERVICE_CONSUME_DIGEST;

    private final ConcurrentMap<String, SimpleServiceProviderModel> providers = new ConcurrentHashMap<String, SimpleServiceProviderModel>();
    private final ConcurrentMap<String, SimpleServiceConsumerModel> consumers = new ConcurrentHashMap<String, SimpleServiceConsumerModel>();
    //private final ConcurrentMap<Integer, ProviderMethodModel> providerCoders = new ConcurrentHashMap<Integer, ProviderMethodModel>();

    public final static ApplicationModel model = new ApplicationModel();

    public static ApplicationModel instance() {
        return model;
    }

    public SimpleServiceProviderModel getProvidedServiceModel(String serviceName) {
        return providers.get(serviceName);
    }


    public SimpleServiceConsumerModel getConsumedServiceModel(String serviceName) {
        return consumers.get(serviceName);
    }

    public List<SimpleServiceProviderModel> allConsumedServices() {
        return new ArrayList<SimpleServiceProviderModel>(providers.values());
    }

    public List<SimpleServiceConsumerModel> allProvidedServices() {
        return new ArrayList<SimpleServiceConsumerModel>(consumers.values());
    }

    public boolean initConsumerService(String serviceName, SimpleServiceConsumerModel serviceModel) {
        if (consumers.putIfAbsent(serviceName, serviceModel) != null) {
            logger_consume.warn("Already register the same:" + serviceName);
            return false;
        }
        return true;
    }

    /**
     * make sure thread safe for init data
     * 
     * @param serviceName
     * @param serviceModel
     */
    /*public void initProviderService(String serviceName, ProviderServiceModel serviceModel) {
        if (Thread.currentThread() != loadThread) {
            LOGGER.warn("Init provider service must be in the same thread:" + serviceName + "|"
                    + Thread.currentThread() + "|" + loadThread);
            // throw new RuntimeException("Init service must be in the same thread");
        }

        ProviderServiceModel oldModel;
        if ((oldModel = this.providedServices.put(serviceName, serviceModel)) != null) {
            ServiceMetadata oldMetadata = oldModel.getMetadata();
            if(!oldMetadata.getGroup().equals(serviceModel.getMetadata().getGroup()))
                throw new IllegalStateException("检测到有同一个服务的不同分组被注册,不允许一个服务配置多个group,想区分服务请使用version.");
            LOGGER.warn("Already register the same service :" + serviceName);
        }

        for (ProviderMethodModel methodModel : serviceModel.getAllMethods()) {
            providerCoders.put(methodModel.getIndex(), methodModel);
        }
        this.isRefresh = true;
    }*/

}
