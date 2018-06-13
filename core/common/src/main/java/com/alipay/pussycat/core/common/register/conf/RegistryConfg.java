package com.alipay.pussycat.core.common.register.conf;

import com.alipay.pussycat.core.common.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午6:54 
 * @version V1.0
 *
 */
public class RegistryConfg extends AbstractConfig{

    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private String address;

    /**
     * The Parameters. 自定义参数
     */
    protected Map<String, String> parameters;

    public String getId() {
        if (StringUtils.isEmpty(id)) {
            id = "RPC-REGISTRY-CONFIG-" + ID_GENERATOR.getAndIncrement();
        }
        return id;
    }
}
