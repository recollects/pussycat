package com.alipay.pussycat.register;

import com.alipay.pussycat.common.model.Result;
import com.alipay.pussycat.publish.model.ServiceMetadata;

/**
 * 该接口定位为服务的注册与订阅使用
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:34
 */
public interface ServerRegisterService {

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param event
     * @return
     */
    boolean register(ServiceMetadata event);

    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param event
     * @return
     */
    boolean subscribe(ServiceMetadata event);

    /**
     *
     * @param event
     * @return
     */
    Result<Boolean> update(ServiceMetadata event);

    /**
     *
     * @param event
     * @return
     */
    Result<Boolean> remove(ServiceMetadata event);

}
