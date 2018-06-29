package com.alipay.pussycat.core.common.register;

import com.alipay.pussycat.core.common.enums.RegisterEnum;
import com.alipay.pussycat.core.common.register.conf.ConsumerConfig;
import com.alipay.pussycat.core.common.register.conf.ProviderConfig;

/**
 *
 * @author recollects
 * @date 2018年06月13日 上午9:48 
 * @version V1.0
 *
 */
public interface Registry {

    /**
     * 启动
     *
     * @return
     */
    boolean isStart();

    /**
     * 注册
     * @param config
     */
    void register(ProviderConfig config);

    /**
     * 从注册中心卸载
     * @param config
     */
    void unregister(ProviderConfig config);

    /**
     * 订阅
     *
     * @param config
     * @return
     */
    ProviderGroup subscribe(ConsumerConfig config);

    /**
     *
     * @return
     */
    RegisterEnum registrar();

}
