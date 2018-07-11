package com.alipay.pussycat.core.common.utils;

/**
 *
 * @author recollects
 * @date 2018年06月29日 上午9:12 
 * @version V1.0
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 当前系统毫秒时间
     * @return
     */
    public static long now(){
        return System.currentTimeMillis();
    }

}
