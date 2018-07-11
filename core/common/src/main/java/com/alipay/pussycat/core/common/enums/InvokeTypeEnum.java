package com.alipay.pussycat.core.common.enums;

import com.alipay.pussycat.core.common.utils.StringUtils;

/**
 *
 * @author recollects
 * @date 2018年07月05日 下午3:46 
 * @version V1.0
 *
 */
public enum InvokeTypeEnum {

    SYNC("SYNC", "同步调用"),
    ONEWAY("ONEWAY", "单向调用"),
    CALLBACK("CALLBACK", "可回调"),
    FUTURE("FUTURE", "future调用");

    String code;
    String desc;

    InvokeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param code
     * @return
     */
    public static InvokeTypeEnum nameOf(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }

        for (InvokeTypeEnum typeEnum : values()) {
            if (StringUtils.equalsIgnoreCase(code, typeEnum.code)) {
                return typeEnum;
            }
        }
        return null;
    }

}
