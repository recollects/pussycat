package com.alipay.pussycat.common.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * tostring style 工具
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午4:27
 */
public class ToStringUtil {

    /**
     *
     * @param obj
     * @return
     */
    public static String defaultStyle(Object obj) {
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.DEFAULT_STYLE);
    }
}
