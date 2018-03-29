package com.alipay.pussycat.publish.exception;

import com.alipay.pussycat.common.model.ErrorCodeEnum;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月29日 下午9:54
 */
public class PussycatException  extends RuntimeException{

    /**
     * 错误码
     */
    protected ErrorCodeEnum errorCode;

    public PussycatException(ErrorCodeEnum errorCode){
        this.errorCode=errorCode;
    }

    public PussycatException(ErrorCodeEnum errorCode,String message){
        super();
        this.errorCode=errorCode;
    }
    public PussycatException(ErrorCodeEnum errorCode,String message, Throwable e){
        super(message,e);
        this.errorCode=errorCode;
    }
    public PussycatException(ErrorCodeEnum errorCode, Throwable e){
        super(e);
        this.errorCode=errorCode;
    }

    public ErrorCodeEnum errorCode() {
        return errorCode;
    }
}
