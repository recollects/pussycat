package com.alipay.pussycat.cache;

import com.alipay.pussycat.cache.model.CacheEnum;


/**
 * 缓存统一管理
 *
 * @author jiadong
 * @date 2018/3/12 15:14
 */
public interface CacheManager {

    /**
     * 添加数据到缓存
     * @param key
     * @param value
     * @return
     */
    boolean set(String key,String value);

    /**
     * 从缓存中取数据
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 删除缓存数据
     *
     * @param key
     * @return
     */
    boolean del(String key);

    /**
     * 缓存实例
     *
     * @return
     */
    CacheEnum cacheName();

}
