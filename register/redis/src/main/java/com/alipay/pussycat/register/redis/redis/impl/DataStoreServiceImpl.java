/**
 * High-Speed Service Framework (HSF)
 *
 * www.taobao.com
 * 	(C) �Ա�(�й�) 2003-2011
 */
package com.alipay.pussycat.register.redis.redis.impl;

import com.alipay.pussycat.register.redis.common.DataStoreService;

import java.util.HashMap;
import java.util.Map;

/**
 * created by sumj
 */
public class DataStoreServiceImpl implements DataStoreService {

    // <组件类名或标识, <数据名, 数据值>>
    private Map<String, Map<String, Object>> datas = new HashMap<String, Map<String, Object>>();

    @SuppressWarnings("unchecked")
    public <T> T get(String componentName, String key) {
        if (!datas.containsKey(componentName)) {
            return null;
        }
        return (T) datas.get(componentName).get(key);
    }

    public void put(String componentName, String key, Object value) {
        Map<String, Object> componentDatas = null;
        if (!datas.containsKey(componentName)) {
            componentDatas = new HashMap<String, Object>();
        } else {
            componentDatas = datas.get(componentName);
        }
        componentDatas.put(key, value);
        datas.put(componentName, componentDatas);
    }

    public void remove(String componentName, String key) {
        if (!datas.containsKey(componentName)) {
            return;
        }
        datas.get(componentName).remove(key);
    }
}
