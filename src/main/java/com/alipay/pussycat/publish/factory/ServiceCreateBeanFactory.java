package com.alipay.pussycat.publish.factory;

import com.google.common.base.Preconditions;

import java.util.function.Predicate;

/**
 * 提供給客戶端发布服务用,一個生产bean的工厂
 * <p>到时候配置服务发布<p/>
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:53
 */
public class ServiceCreateBeanFactory {

    private static final int DEFAULT_TIMEOUT = 3000;
    private static final String DEFAULT_VERSION = "1.0";

    private int timeout = DEFAULT_TIMEOUT;

    private String version = DEFAULT_VERSION;

    private Class clazz;

    private Class ref;

    /**
     *
     */
    private void check() {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(ref);
        Preconditions.checkArgument(timeout > 0, "超时时间不能小于0");
    }

    /**
     * TODO 处理逻辑需要提出去,还有创建完成发布服务也要单独提一个接口处理,
     * 尽量做到不同逻辑之间互相独立
     */
    private void createBean(){
        check();
        //TODO 创建bean服务
    }

    /**
     *
     */
//    public void publish() {
//
//    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Class getRef() {
        return ref;
    }

    public void setRef(Class ref) {
        this.ref = ref;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
