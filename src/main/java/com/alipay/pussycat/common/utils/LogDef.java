package com.alipay.pussycat.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月27日 下午10:20
 */
public class LogDef {


    /**---------------------------缓存摘要日誌--------------------------------------------*/
    public static final Logger CACHE_DIGEST = LoggerFactory.getLogger("cache-digest");

    /**---------------------------服务发布摘要日誌--------------------------------------------*/
    public static final Logger SERVICE_PUBLISH_DIGEST = LoggerFactory.getLogger("service-publish-digest");

    /**---------------------------服务注册摘要日誌--------------------------------------------*/
    public static final Logger SERVICE_REGISTER_DIGEST = LoggerFactory.getLogger("service-register-digest");
    /**---------------------------服务消费摘要日誌--------------------------------------------*/
    public static final Logger SERVICE_CONSUME_DIGEST = LoggerFactory.getLogger("service-register-digest");

    /**---------------------------服务消费摘要日誌--------------------------------------------*/
    public static final Logger SERVICE_METADATA_DIGEST = LoggerFactory.getLogger("service-register-digest");

    public static final Logger SERVICE_LISTENER_DIGEST = LoggerFactory.getLogger("service-listener-digest");


}
