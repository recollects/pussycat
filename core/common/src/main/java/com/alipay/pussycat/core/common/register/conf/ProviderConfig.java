package com.alipay.pussycat.core.common.register.conf;

import com.alipay.pussycat.core.common.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author recollects
 * @date 2018年06月13日 下午7:02 
 * @version V1.0
 *
 */
public class ProviderConfig<T> extends AbstractConfig {

    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    /**
     * 接口唯一标识[这个用uuid来生成唯一标识]
     */
    private String uniqueId;

    /**
     * 这个是接口唯一标识[com.alipay.userService#1.0.0]
     */
    private String intefaceId;

    private Map<String, MethodConfig> methods;

    private boolean register;

    private T ref;

    /**
     * 包涵的方法[添加特定的注解可以使其方法不发布不可被调用,还可以减少客户端恶意调用]
     */
    private String include;

    /**
     * 排除的方法[类似包涵的方法]
     */
    private String exclude;

    /**
     * 服务优先级[还可以加入一些权重属性,后期慢慢加]
     */
    private int priority;

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

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    protected String getId() {
        if (StringUtils.isEmpty(id)) {
            id = "RPC-PROVIDER-CONFIG-" + ID_GENERATOR.getAndIncrement();
        }
        return id;
    }

}
