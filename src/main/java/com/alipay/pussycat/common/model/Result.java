package com.alipay.pussycat.common.model;

import com.alipay.pussycat.common.utils.ToStringStyleUtil;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 接口调用返回数据
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午4:18
 */
public class Result<T> implements Serializable{

    private static final long serialVersionUID = -7439691665727292793L;

    /**
     * 通讯是否成功标识
     */
    private boolean success;

    /**
     * 接口调用返回结果
     */
    private T content;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 唯一请求ID
     */
    private String requestId;

    public Result(boolean success){
        this.success=success;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return ToStringStyleUtil.defaultStyle(this);
    }

}

