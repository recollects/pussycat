package com.alipay.pussycat.consumer.proxy;

import com.alipay.pussycat.consumer.future.DefaultInvokerFuture;
import com.alipay.pussycat.consumer.future.InvokerFuture;
import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.RpcCommonResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author recollects
 * @date 2018年06月29日 下午3:43 
 * @version V1.0
 *
 */
public class JDKInvocationHandler implements InvocationHandler {

    private Class proxyClass;

    public JDKInvocationHandler(Class proxyClass) {
        this.proxyClass = proxyClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        Class[] paramTypes = method.getParameterTypes();

        //TODO 发起远程调用


        //构造请求
        PussycatRequest request = buildRequest();

        InvokerFuture invokerFuture = new DefaultInvokerFuture();

        //TODO 这里invokerFuture接口只能提供future模式来调用，不满足其它调用模式

        RpcCommonResponse response = invokerFuture.waitResponse();


        return response;
    }

    /**
     *
     * @return
     */
    private PussycatRequest buildRequest() {
        return null;
    }
}
