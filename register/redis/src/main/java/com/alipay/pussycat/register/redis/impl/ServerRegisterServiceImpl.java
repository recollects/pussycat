package com.alipay.pussycat.register.redis.impl;

import com.alipay.pussycat.core.common.model.*;
import com.alipay.pussycat.core.common.utils.LogDef;
import com.alipay.pussycat.core.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.register.redis.ServerRegisterService;
import com.alipay.pussycat.register.redis.redis.service.CacheManager;
import org.slf4j.Logger;

import java.util.Map;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:38
 */
public class ServerRegisterServiceImpl implements ServerRegisterService {

    protected static final Logger logger_publish = LogDef.SERVICE_PUBLISH_DIGEST;

    private CacheManager cacheManager = PussycatServiceContainer.getInstance(CacheManager.class);

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param metadata
     * @return
     */
    @Override
    public boolean register(ServiceMetadata metadata) {

        // 将提供方服务存到注册中心去
        SimpleServiceProviderModel simpleServiceProviderModel = new SimpleServiceProviderModel(metadata);

        String key = PussycatContants.PUSSYCAT_REDIS_KEY_PRE + metadata.getUniqueName();
        return cacheManager.setObj(key, simpleServiceProviderModel);
    }

    @Override
    public SimpleServiceProviderModel subscribe(ServiceMetadata metadata) {
        SimpleServiceProviderModel providerMode = null;
        try {
            //获取注册中心服务信息
            String key = PussycatContants.PUSSYCAT_REDIS_KEY_PRE + metadata.getUniqueName();
            providerMode = (SimpleServiceProviderModel) cacheManager.getObj(key);
            //更新至本地缓存信息
            Map<String, ProviderMethodModel> providerMethodModels = providerMode.getProviderMethodModels();
            ApplicationModel.instance().getProviders().putIfAbsent(providerMode.getServiceName(), providerMode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return providerMode;
    }

    @Override
    public Result<Boolean> update(ServiceMetadata event) {
        return new Result(false);
    }

    @Override
    public Result<Boolean> remove(ServiceMetadata event) {
        return new Result(false);
    }

}
