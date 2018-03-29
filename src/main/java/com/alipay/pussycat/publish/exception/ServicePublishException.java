package com.alipay.pussycat.publish.exception;

import com.alipay.pussycat.common.model.ErrorCodeEnum;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月29日 下午9:51
 */
public class ServicePublishException extends PussycatException {

    /**
     *
     * @param errorCode
     * @param message
     * @param e
     */
    public ServicePublishException(ErrorCodeEnum errorCode, String message, Throwable e) {
        super(errorCode, message, e);
    }
}
