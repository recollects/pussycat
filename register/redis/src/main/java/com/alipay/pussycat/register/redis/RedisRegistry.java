package com.alipay.pussycat.register.redis;

import com.alipay.pussycat.core.common.register.AbstractRegistry;
import com.alipay.pussycat.core.common.enums.RegisterEnum;
import com.alipay.pussycat.core.common.register.ProviderGroup;
import com.alipay.pussycat.core.common.register.conf.ConsumerConfig;
import com.alipay.pussycat.core.common.register.conf.ProviderConfig;

import java.util.List;

/**
 *
 * @author recollects
 * @date 2018年06月13日 上午9:51 
 * @version V1.0
 *
 */
public class RedisRegistry extends AbstractRegistry {

    @Override
    public boolean isStart() {
        return false;
    }

    @Override
    public void register(ProviderConfig config) {

    }

    @Override
    public void unregister(ProviderConfig config) {

    }

    @Override
    public List<ProviderGroup> subscribe(ConsumerConfig config) {
        return null;
    }

    @Override
    public RegisterEnum registrar() {
        return RegisterEnum.REDIS;
    }

}
