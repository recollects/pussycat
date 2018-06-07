package com.alipay.pussycat.core.common.enums;

/**
 * 异常码
 *
 * @author wb-smj330392
 * @create 2018-05-26 14:57
 */
public enum PussycatExceptionEnum {

    E_10000("10000", "发布错误"),
    E_10001("10001 ", "注册错误"),
    E_10002("10002", "服务不可用"),
    E_10003("10003", "订阅错误"),
    E_10004("10004", "服务类型不是接口"),
    E_10005("10005", "实际服务对象没有实现指定接口"),
    E_10006("10006", "请求参数类型不匹配"),
    E_10007("10007", "服务端口启动出错"),;

    String errorCode    = "";
    String errorMessage = "";

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private PussycatExceptionEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
