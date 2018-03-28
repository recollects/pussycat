package com.alipay.pussycat.cache;

import java.util.List;
import java.util.function.Consumer;

import com.alipay.pussycat.cache.model.CacheEnum;
import com.alipay.pussycat.common.utils.LogDef;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

/**
 * 缓存服务管理工厂
 *
 * @author jiadong
 * @date 2018/3/12 15:57
 */
public class CacheManagerFactory {

    private static final Logger logger = LogDef.CACHE_DIGEST;

    private List<CacheManager> cacheList = Lists.newArrayList();

    /**
     * 取出缓存服务
     *
     * @param cacheName
     * @return
     */
    public CacheManager get(CacheEnum cacheName) {

        if (logger.isDebugEnabled()) {
            logger.debug("获取缓存实例" + cacheName.name());
        }
        if (CollectionUtils.isEmpty(cacheList)) {
            logger.error("缓存实例异常...");
            throw new RuntimeException("缓存实例异常...");
        }

        return Iterators.find(cacheList.iterator(), new Predicate<CacheManager>() {

            @Override
            public boolean apply(CacheManager manager) {
                if (cacheName == manager.cacheName()) {
                    return true;
                }
                return false;
            }
        });
    }

    public void setCacheList(List<CacheManager> cacheList) {
        this.cacheList = cacheList;
    }
}
