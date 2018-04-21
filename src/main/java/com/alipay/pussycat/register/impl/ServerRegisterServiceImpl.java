package com.alipay.pussycat.register.impl;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.redis.impl.RedisCacheManagerImpl;
import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.model.Result;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.publish.model.ServiceMetadata;
import com.alipay.pussycat.publish.model.SimpleServiceModel;
import com.alipay.pussycat.register.DataStoreService;
import com.alipay.pussycat.register.ServerRegisterService;
import com.alipay.pussycat.server.ProviderServer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:38
 */
public class ServerRegisterServiceImpl implements ServerRegisterService {

    protected static final Logger logger_publish = LogDef.SERVICE_PUBLISH_DIGEST;

    private DataStoreService dataStoreService = PussycatServiceContainer.getInstance(DataStoreService.class);
    private CacheManager cacheManager = PussycatServiceContainer.getInstance(CacheManager.class);

    @Autowired
    private ProviderServer providerServer;

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param metadata
     * @return
     */
    @Override
    public boolean register(ServiceMetadata metadata) {
        //开启pussycat服务
        try {
            providerServer.startPYCServer();
        } catch (Exception e) {
            logger_publish.info("start pussycat server fail......");
        }

        //TODO 将提供方服务存到注册中心去
        SimpleServiceModel simpleServiceModel = new SimpleServiceModel(metadata.getInterfaceName(), metadata, metadata.getTarget());

        dataStoreService.put(PussycatContants.PAT_METADATA_KEY, metadata.getUniqueName(), simpleServiceModel);
        String key = PussycatContants.PUSSYCAT_REDIS_KEY_PRE + metadata.getUniqueName();
        return ((RedisCacheManagerImpl) cacheManager).set(key, simpleServiceModel);
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
