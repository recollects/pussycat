package com.alipay.pussycat.consumer.proxy;

import com.alipay.pussycat.consumer.invoker.ConsumerInvoker;
import com.alipay.pussycat.consumer.invoker.Invoker;
import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.PussycatResponse;

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

        Invoker invoker=new ConsumerInvoker();

        if ("toString".equals(methodName) && paramTypes.length == 0) {
            return invoker.toString();
        } else if ("hashCode".equals(methodName) && paramTypes.length == 0) {
            return invoker.hashCode();
        } else if ("equals".equals(methodName) && paramTypes.length == 1) {
            return invoker.equals(proxy);
        }


        PussycatResponse response = invoker.invoker(buildRequest());

        return response;
    }

    /**
     *
     * @return
     */
    private PussycatRequest buildRequest() {
        //TODO
        return null;
    }
}
