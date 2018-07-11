package com.alipay.pussycat.core.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对服务端接口方法作的治理方案
 *
 * @author wb-smj330392
 * @create 2018-06-12 17:29
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ServiceMethodController {

    /**
     * 降级的两种场景：1 某些特殊场景下手动设置；2 当调用成功率低于某个值时实现降级
     * @return
     */
    boolean isSupportDegrade() default false;


    boolean isSupportCallLimit() default true;

    /**
     * 接口降级后的mock返回路径
     * @return
     */
    String degradePath();

    /**
     * 一分钟最大调用次数
     * @return
     */
    int maxCallCount();

    /**
     *  调用成功率达到百分之90
     * @return
     */
    int degradePercent();
}