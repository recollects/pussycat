package com.alipay.pussycat.serializable.model;

import java.io.Serializable;

/**
 * Created by recollects on 18/3/13.
 */
public class TransportModel implements Serializable{


    private static final long serialVersionUID = 7109955232132227839L;

    private String serviceName;

    private Class objectType;

    private Object result;

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public void setObjectType(Class objectType) {
        this.objectType = objectType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Class getObjectType() {
        return objectType;
    }
}
