package com.alipay.pussycat.core.api.annotation;

import java.lang.annotation.*;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年06月11日 下午2:59
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Service {

    /**
     * 版本号
     * @return
     */
    String version() ;

    /**
     * 超时时间
     * @return
     */
    int timeout() ;

    /**
     * 接口名
     * @return
     */
    String interfaceName() default "";

    /**
     * 安全考虑,或者基于白名单的方式都可以,预留
     * @return
     */
    String white() default "";

    /**
     * 项目启动是否注册到注册中心
     * @return
     */
    boolean register() default false;

    /**
     * 异步调用,给客户端一个future
     * @return
     */
    boolean async() default false;

    /**
     * 注册地址
     * @return
     */
    //String[] registry() default {};

    /**
     * 权重   1-100
     * @return
     */
    int weight() default 50;

}
