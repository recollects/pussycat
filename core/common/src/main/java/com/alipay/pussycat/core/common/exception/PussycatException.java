package com.alipay.pussycat.core.common.exception;

import com.alipay.pussycat.core.common.enums.PussycatExceptionEnum;

/**
 *
 * @author wb-smj330392
 * @create 2018-05-26 14:48
 */
public class PussycatException extends RuntimeException {

    private String msnCode;
    private String msnInfo;

    public PussycatException(PussycatExceptionEnum pussycatExceptionEnum) {
        this(pussycatExceptionEnum.getErrorCode(), pussycatExceptionEnum.getErrorMessage());
    }

    public PussycatException(String msnCode, String msnInfo) {
        this.msnCode = msnCode;
        this.msnInfo = msnInfo;
    }
}
