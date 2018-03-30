package com.alipay.pussycat.publish;

import com.alipay.pussycat.common.model.ErrorCodeEnum;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.publish.exception.ServicePublishException;
import com.alipay.pussycat.publish.model.ServiceEvent;
import com.alipay.pussycat.publish.model.SimpleServiceModel;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.function.Predicate;

/**
 * 提供給客戶端发布服务用[客户端所有服务都由这个类作为出口]
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:53
 */
public class PussycatServiceExporter {

    private static final Logger LOGGER = LogDef.SERVICE_PUBLISH_DIGEST;

    private static final int DEFAULT_TIMEOUT = 3000;
    private static final String DEFAULT_VERSION = "1.0";

    private int timeout = DEFAULT_TIMEOUT;

    private String version = DEFAULT_VERSION;

    private Class clazz;

    private Class ref;

    @Autowired
    private ServiceEventPublisher serviceEventPublisher;

    /**
     *
     */
    private void check() {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(ref);
        Preconditions.checkArgument(timeout > 0, "超时时间不能小于0");
    }

    /**
     * 必须调用
     */
    public void init(){
        check();

        SimpleServiceModel model = new SimpleServiceModel();
        model.setClazz(getClazz());
        model.setTimeout(getTimeout());
        model.setVersion(getVersion());
        try {
            serviceEventPublisher.publish(model);
        } catch (ServicePublishException e) {
            LOGGER.error("服务初始异常,class={}",clazz,e);
            throw new ServicePublishException(ErrorCodeEnum.UNKNOWN_SYSTEM_ERROR,"服务初始异常",e);
        }
    }

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
