package com.alipay.pussycat.register.impl;

import com.alipay.pussycat.common.model.Result;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.publish.model.ServiceEvent;
import com.alipay.pussycat.register.ServerRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:38
 */
@Service
public class ServerRegisterServiceImpl implements ServerRegisterService{

    protected static final Logger logger_register = LogDef.SERVICE_REGISTER_DIGEST;
    protected static final Logger logger = LoggerFactory.getLogger(ServerRegisterServiceImpl.class);

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param event
     * @return
     */
    @Override
    public Result<Boolean> register(ServiceEvent event) {
        //TODO 将提供方服务存到注册中心去
        logger.debug("注册中心收到注册消息:{}",event);
        return new Result(true);
    }

    @Override
    public Result<Boolean> update(ServiceEvent event) {
        return null;
    }

    @Override
    public Result<Boolean> remove(ServiceEvent event) {
        return null;
    }

}
