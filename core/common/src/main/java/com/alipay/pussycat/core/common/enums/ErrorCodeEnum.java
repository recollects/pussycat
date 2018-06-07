package com.alipay.pussycat.core.common.enums;

/**
 * 错误码
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午8:37
 */
public enum ErrorCodeEnum implements java.io.Serializable {

    /**
     * 未知异常
     */
    UNKNOWN_ERROR("UNKNOWN_ERROR", "AE0599999999", "未知异常"),

    /**
     * 未知系统异常异常
     */
    UNKNOWN_SYSTEM_ERROR("UNKNOWN_SYSTEM_ERROR", "AE0509999999", "未知系统异常异常"),

    /**
     * 未知业务异常
     */
    UNKNOWN_BIZ_ERROR("UNKNOWN_BIZ_ERROR", "AE0519999999", "未知业务异常"),

    /**
     * 未知第三方异常
     */
    UNKNOWN_THIRD_PARTY_ERROR("UNKNOWN_THIRD_PARTY_ERROR", "AE0529999999", "未知第三方异常"),

    /**
     * 错误码工具内部异常
     */
    CODE_PROCESSING_ERROR("CODE_PROCESSING_ERROR", "AE0509998998", "错误码工具内部异常");

    private String name;
    private String code;
    private String desc;

    ErrorCodeEnum(String name, String code, String desc) {
        this.name = name;
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
