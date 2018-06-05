/**
 * High-Speed Service Framework (HSF)
 *
 * www.taobao.com
 * 	(C) �Ա�(�й�) 2003-2011
 */
package com.alipay.pussycat.register.common;

/**
 * 描述：用于存储服务的数据
 *
 * created by sumj
 */
public interface DataStoreService {

    /**
     * 获取存储的OSGi服务的数据
     *
     * @param componentName
     * @param key
     * @return Object
     */
    <T> T get(String componentName, String key);

    /**
     * 放入需要存储的OSGi服务的数据
     *
     * @param componentName
     * @param key
     * @param value
     */
    void put(String componentName, String key, Object value);

    /**
     * 删除存储的OSGi服务的数据
     *
     * @param componentName
     * @param key
     */
    void remove(String componentName, String key);
}
