package com.alipay.pussycat.register.impl;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.redis.impl.RedisCacheManagerImpl;
import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.model.Result;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.publish.model.ServiceMetadata;
import com.alipay.pussycat.register.DataStoreService;
import com.alipay.pussycat.register.ServerRegisterService;

/**
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:38
 */
public class ServerRegisterServiceImpl implements ServerRegisterService{

    private DataStoreService dataStoreService = PussycatServiceContainer.getInstance(DataStoreService.class);
    private CacheManager cacheManager = PussycatServiceContainer.getInstance(CacheManager.class);

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param metadata
     * @return
     */
    @Override
    public Result<Boolean> register(ServiceMetadata metadata) {
        //TODO 将提供方服务存到注册中心去
        dataStoreService.put(PussycatContants.PAT_METADATA_KEY,metadata.getUniqueName(),metadata);
        String key = PussycatContants.PUSSYCAT_REDIS_KEY_PRE +  metadata.getUniqueName();
        ((RedisCacheManagerImpl)cacheManager).set(key,metadata.getMethodStamp());
        return new Result(true);
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
