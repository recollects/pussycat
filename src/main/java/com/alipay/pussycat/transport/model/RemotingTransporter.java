package com.alipay.pussycat.transport.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 网络传输对象
 * 对于网络请求或响应的总控制类
 * @author wb-smj330392
 * @create 2018-05-25 15:09
 */
public class RemotingTransporter implements Serializable {

    private static final long serialVersionUID = -750012243114836795L;
    /**
     * 定义的网络传输的类型：请求｜响应
     */
    private int transportType;

    /**
     * 定义的网络传输的类型：请求 code=1｜响应 code =2
     * 主要用于网络协议头的部分
     */
    private byte code;

    private int timeOut = 3000;

    public TransportItemBytes getTransportItemBytes() {
        return transportItemBytes;
    }

    public void setTransportItemBytes(TransportItemBytes transportItemBytes) {
        this.transportItemBytes = transportItemBytes;
    }

    /**
     * 传输主体在网络中的字节信息
     */
    private TransportItemBytes transportItemBytes;

    /**
     * 定义的网络传输的主体信息
     */
    private TransportBody transportBody;

    /**
     * 请求的时间戳
     */
    private transient long timestamp;

    private static final AtomicLong transportId = new AtomicLong(1);

    /**
     * 请求的id
     */
    private long requestId = transportId.getAndIncrement();

    public RemotingTransporter() {

    }

    /**
     * 创建一个请求传输对象
     * @param transportBody 请求的正文
     * @return
     */
    public static RemotingTransporter createRequestTransporter(TransportBody transportBody) {
        RemotingTransporter remotingTransporter = new RemotingTransporter();
        remotingTransporter.transportBody = transportBody;
        remotingTransporter.setCode(TransportProtocal.REQUEST_CODE);
        remotingTransporter.transportType = TransportProtocal.REQUEST_REMOTING;
        return remotingTransporter;
    }

    /**
     * 创建一个响应对象
     * @param code 响应对象的类型
     * @return
     */
    public static RemotingTransporter createResponseTransporter(TransportBody transportBody, long requestId) {
        RemotingTransporter remotingTransporter = new RemotingTransporter();
        remotingTransporter.transportBody = transportBody;
        remotingTransporter.setRequestId(requestId);
        remotingTransporter.setCode(TransportProtocal.REQUEST_CODE);
        remotingTransporter.transportType = TransportProtocal.RESPONSE_REMOTING;
        return remotingTransporter;
    }

    public static RemotingTransporter newInstance(long requestId, byte code, byte[] bytes) {
        RemotingTransporter remotingTransporter = new RemotingTransporter();
        remotingTransporter.setCode(code);
        if (code == TransportProtocal.REQUEST_CODE) {
            remotingTransporter.setTransportType(TransportProtocal.REQUEST_REMOTING);
        } else {
            remotingTransporter.setTransportType(TransportProtocal.RESPONSE_REMOTING);
        }
        remotingTransporter.setRequestId(requestId);
        remotingTransporter.transportItemBytes.setBodyBytes(bytes);
        return remotingTransporter;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    public TransportBody getTransportBody() {
        return transportBody;
    }

    public void setTransportBody(TransportBody transportBody) {
        this.transportBody = transportBody;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
