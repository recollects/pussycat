package com.alipay.pussycat.core.api.annotation;

import java.lang.annotation.*;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年06月11日 下午3:00
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface Reference {

    /**
     *接口名
     *
     * @return
     */
    String interfaceName() default "";

    /**
     *
     * 版本号
     * @return
     */
    String version() default "";

    /**
     *url,如果有地址就采用url地址方式,如果没有就去注册中心获取
     *
     * @return
     */
    String[] url() default "";

    /**
     *代理方式
     *
     * @return
     */
    String proxy() default "";

    /**
     *超时时间
     * @return
     */
    int timeout() default 0;

    /**
     * 是否校验,启动的时候是否先校验一下提供服务端是否通
     *
     * @return
     */
    boolean check() default false;
}
