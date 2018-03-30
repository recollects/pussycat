package com.alipay.pussycat.publish.model;

/**
 *
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月29日 下午9:45
 */
public class SimpleServiceModel {

    private int timeout ;

    private String version;

    private Class clazz;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Class getClazz() {
        return clazz;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getVersion() {
        return version;
    }
}
