package com.alipay.pussycat.register.impl;

import com.alipay.pussycat.common.model.Result;
import com.alipay.pussycat.publish.model.ServiceEvent;
import com.alipay.pussycat.register.ServerRegisterService;

/**
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:38
 */
public class ServerRegisterServiceImpl implements ServerRegisterService{

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param event
     * @return
     */
    @Override
    public Result<Boolean> register(ServiceEvent event) {
        //TODO 将提供方服务存到注册中心去
        return new Result(false);
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
