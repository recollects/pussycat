package com.alipay.pussycat.publish.model;

import java.io.Serializable;

/**
 *
 * 网络传输包涵接口信息,Ip,port等
 *
 * TODO jiadong 先放着,需要考虑考虑这部分
 *
 * Created by recollects on 18/3/12.
 */
public abstract class ServiceEvent implements Serializable{

    private static final long serialVersionUID = 1L;

    private final long timestamp;

    public ServiceEvent(){
        timestamp=System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
