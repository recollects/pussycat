package com.alipay.pussycat.core.common.model;

/**
 * 网络传输的一些协议信息
 * @author wb-smj330392
 * @create 2018-05-25 15:26
 */
public class TransportProtocal {

    /** 协议头长度 */
    public static final int HEAD_LENGTH = 16;

    /** CAFE  协议头 */
    public static final short CAFE = (short) 0xcafe;

    /** 向注册中心请求的类型*/
    public static final byte REGISTER_REQUEST_CODE = 11;

    /** 向注册中心响应的类型*/
    public static final byte REGISTER_RESPONSE_CODE = 12;

    /** 发送的是请求信息*/
    public static final int REQUEST_REMOTING = 1;

    /** 发送的是响应信息*/
    public static final int RESPONSE_REMOTING = 2;

    //心跳
    public static final byte HEARTBEAT = 127;

    private byte code;
    private long id;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    private int bodyLength;

}


