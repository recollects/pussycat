package com.alipay.pussycat.core.common.register.conf;

import com.alipay.pussycat.core.common.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午7:16 
 * @version V1.0
 *
 */
public class ConsumerConfig<T> extends AbstractConfig {

    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    /**
     * 接口唯一标识[这个用uuid来生成唯一标识]
     */
    private String uniqueId;

    /**
     * 这个是接口唯一标识[com.alipay.userService#1.0.0]
     */
    private String intefaceId;

    private Class<T> proxyClass;

    private Map<String, MethodConfig> methods;

    /**
     * 负载均衡策略
     */
    private String loadBalancer;

    /**
     * 调用类型【sync：同步调用，oneway：单向调用不需要返回，callback：需要服务端回调，future：调用不需要立即返回可阻塞等待返回结果】
     * {@link com.alipay.pussycat.core.common.enums.InvokeTypeEnum}
     */
    private String invokeType;

    /**
     * 是否启动校验[强依赖校验]
     */
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getIntefaceId() {
        return intefaceId;
    }

    public void setIntefaceId(String intefaceId) {
        this.intefaceId = intefaceId;
    }

    public Map<String, MethodConfig> getMethods() {
        return methods;
    }

    public void setMethods(
            Map<String, MethodConfig> methods) {
        this.methods = methods;
    }

    public String getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(String loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }

    public String getInvokeType() {
        return invokeType;
    }

    public void setProxyClass(Class<T> proxyClass) {
        this.proxyClass = proxyClass;
    }

    public Class<T> getProxyClass() {
        return proxyClass;
    }

    @Override
    protected String getId() {
        if (StringUtils.isEmpty(id)) {
            id = "RPC-CONSUMER-CONFIG-" + ID_GENERATOR.getAndIncrement();
        }
        return id;
    }
}
