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
            //FIXME 明瑾 注册服务不需要每个都调用这个接口
            providerServer.startPYCServer();
        } catch (Exception e) {
            logger_publish.info("start pussycat server fail......");
        }

        //TODO 将提供方服务存到注册中心去
        //FIXME 明瑾 这里参数传递,直接传一个对象就好了,或者传必须的参数metadata都传过去了
        SimpleServiceModel simpleServiceModel = new SimpleServiceModel(metadata.getInterfaceName(), metadata, metadata.getTarget());

        //FIXME 明瑾 这里put操作,就是一个key-value操作,要么把key在这里拼接,要么放到dataStoreService实现里去拼接
        dataStoreService.put(PussycatContants.PAT_METADATA_KEY, metadata.getUniqueName(), simpleServiceModel);
        String key = PussycatContants.PUSSYCAT_REDIS_KEY_PRE + metadata.getUniqueName();
        //FIXME 明瑾 这里略...
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
