package com.alipay.pussycat.core.common.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年06月07日 下午7:52
 */
@Component
@ConfigurationProperties
public class RedisConfig {

    private int redisMaxIdle=300;
    private int redisMaxTotal=6000;
    private int redisMinIdle=100;
    private int redisMaxWaitMillis=1000;
    private boolean redisTestOnBorrow=true;
    private String redisHost="127.0.0.1";
    private int redisPport=6379;
    private String redisPass="";
    private int redisExpiration=600000;

}
