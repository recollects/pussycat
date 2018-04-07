package com.alipay.pussycat.cache;

import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.StringUtils;
import com.alipay.pussycat.context.PussyCatApplicationContext;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;

import java.util.Map;

/**
 * 缓存服务管理工厂
 *
 * @author jiadong
 * @date 2018/3/12 15:57
 */
public class CacheManagerFactory {

    private static final Logger logger = LogDef.CACHE_DIGEST;

    private String cacheName;

    /**
     * 取出缓存服务
     *
     * @return
     */
    public CacheManager get() {

        Map<String, CacheManager> cacheManagerMap = PussyCatApplicationContext.getApplicationContext().getBeansOfType(CacheManager.class);

        if (logger.isDebugEnabled()) {
            logger.debug("获取缓存实例" + cacheName);
        }
        if (MapUtils.isEmpty(cacheManagerMap)) {
            logger.error("缓存实例异常...");
            throw new RuntimeException("缓存实例异常...");
        }

        return Iterators.find(cacheManagerMap.values().iterator(), new Predicate<CacheManager>() {

            @Override
            public boolean apply(CacheManager manager) {
                if (StringUtils.equalsAnyIgnoreCase(cacheName,manager.cacheName().name())) {
                    return true;
                }
                return false;
            }
        });
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
