/*
 * Copyright 2015 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package com.alipay.pussycat.result;

import java.io.Serializable;
import java.util.Map;

/**
 * 类BaseResult.java的实现描述：接口返回的基础类，通过success判断本次调用在服务器端执行是否成功
 * 
 * @author lixj 2015年1月26日 下午3:44:31
 */
public class BaseResult implements Serializable {
    private static final String SUCCESS_CODE     = "200";
    private static final String SUCCESS_MSG      = "success";

    /**
     * 
     */
    private static final long   serialVersionUID = 4398730454548225208L;

    /**
     * 标识本次调用是否执行成功
     */
    private boolean             success;

    /**
     * 本次调用返回errorCode，一般为错误代码
     */
    private String              errorCode;

    /**
     * 本次调用返回的消息，一般为错误消息
     */
    private String              errorMsg;

    /**
     * 扩展字段
     */
    private Map<String, String> errorExtInfo;

    private String              errorLevel;

    /**
     * 请求Id
     */
    private String              requestId;

    public BaseResult() {
        setErrorCode(SUCCESS_CODE);
        setErrorMsg(SUCCESS_MSG);
        this.success = true;
    }

    /**
     * 设置错误信息
     * 
     * @param code
     * @param message
     */
    @SuppressWarnings("unchecked")
    public <R extends BaseResult> R setErrorMessage(String code, String message) {
        setErrorCode(code);
        setErrorMsg(message);
        this.success = false;

        return (R) this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getErrorExtInfo() {
        return errorExtInfo;
    }

    public void setErrorExtInfo(Map<String, String> errorExtInfo) {
        this.errorExtInfo = errorExtInfo;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 返回错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码
     * 
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误消息
     * 
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the errorLevel
     */
    public String getErrorLevel() {
        return errorLevel;
    }

    /**
     * @param errorLevel the errorLevel to set
     * @see
     */
    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

}
