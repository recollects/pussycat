package com.alipay.pussycat.transport.model;

/**
 *
 * 该类表示网络传输时的字节
 * @author wb-smj330392
 * @create 2018-05-25 15:32
 */
public class TransportItemBytes {

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    public void setBodyBytes(byte[] bodyBytes) {
        this.bodyBytes = bodyBytes;
    }

    private byte[] bodyBytes;


    public int size() {
        return bodyBytes == null ? 0 : bodyBytes.length;
    }

}
