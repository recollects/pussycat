package com.alipay.pussycat.register.impl;

import com.alipay.pussycat.cache.CacheManager;
import com.alipay.pussycat.cache.CacheManagerFactory;
import com.alipay.pussycat.common.model.Result;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.StringUtils;
import com.alipay.pussycat.publish.model.ServiceEvent;
import com.alipay.pussycat.register.ServerRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:38
 */
@Service
public class ServerRegisterServiceImpl implements ServerRegisterService {

    protected static final Logger logger_register = LogDef.SERVICE_REGISTER_DIGEST;
    protected static final Logger logger = LoggerFactory.getLogger(ServerRegisterServiceImpl.class);


    @Autowired
    private CacheManagerFactory cacheManagerFactory;

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param event
     * @return
     */
    @Override
    public Result<Boolean> register(ServiceEvent event) {
        //TODO 将提供方服务存到注册中心去
        logger.debug("注册中心收到注册消息:{}", event);
        //分两块
        // 一块是存储接口信息到redis.
        // 第二块是将客户端订阅的接口消息,一旦这里有更新就通知下去

        Object serviceKey = buildServiceKey(event);
        CacheManager cacheManager = cacheManagerFactory.get();
        boolean set = cacheManager.set(serviceKey, event);
        if (set){
            logger.info("注册成功,serviceKey:{},接口信息:{}",serviceKey,event);

            //TODO 这里需要通知调用端
        }

        return new Result(set);
    }

    @Override
    public Result<Boolean> update(ServiceEvent event) {
        Object serviceKey = buildServiceKey(event);
        CacheManager cacheManager = cacheManagerFactory.get();

        Object obj = cacheManager.get(serviceKey);
        if (obj==null){
            logger.warn("执行接口信息更新,"+serviceKey+"服务不存在");
            throw  new RuntimeException(serviceKey+"服务不存在");
        }

        boolean set = cacheManager.set(serviceKey, event);
        //TODO 这里需要通知调用端
        return new Result<>(set);
    }

    @Override
    public Result<Boolean> remove(ServiceEvent event) {
        Object serviceKey = buildServiceKey(event);
        CacheManager cacheManager = cacheManagerFactory.get();

        Object obj = cacheManager.get(serviceKey);
        if (obj==null){
            logger.warn("执行接口信息删除,"+serviceKey+"服务不存在");
            throw  new RuntimeException(serviceKey+"服务不存在");
        }
        boolean del = cacheManager.del(serviceKey);
        //TODO 这里需要通知调用端
        return new Result<>(del);
    }

    /**
     *  构建唯一接口信息
     *
     * @param event
     * @return
     */
    private String buildServiceKey(ServiceEvent event){
        StringBuilder sb = new StringBuilder();
        sb.append("rpc://");
        sb.append(event.getHost()).append(":");
        sb.append(event.getPort()).append("/");
        sb.append(event.getInterfaceName()).append("/");
        sb.append(event.getMethodName()).append("#");
        sb.append(StringUtils.parameterTypesToStr(event.getParameterTypes()));
        return sb.toString();
    }
}
