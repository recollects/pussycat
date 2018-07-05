package com.alipay.pussycat.consumer.invoker;

import com.alipay.pussycat.consumer.future.DefaultInvokerFuture;
import com.alipay.pussycat.consumer.future.InvokerFuture;
import com.alipay.pussycat.core.common.enums.InvokeTypeEnum;
import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.PussycatResponse;
import com.alipay.pussycat.core.common.model.RpcCommonResponse;
import com.alipay.pussycat.core.common.register.ProviderGroup;
import com.alipay.pussycat.core.common.register.Registry;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author recollects
 * @date 2018年06月29日 下午4:15 
 * @version V1.0
 *
 */
public class ConsumerInvoker implements Invoker{

    @Override
    public PussycatResponse invoker(PussycatRequest request) throws PussycatException {

        //TODO 判断调用类型，或者在下层判断
        //负载方式
        //
        Registry registry = null;

        ProviderGroup subscribe = registry.subscribe(null);

        List<ProviderGroup.ProviderInfo> providerInfoList = subscribe.getProviderInfoList();

        //TODO 从服务列表里选一个【随机、轮询、淘汰等等都可以】
        ProviderGroup.ProviderInfo providerInfo = providerInfoList.get(0);

        InvokeTypeEnum typeEnum = InvokeTypeEnum.nameOf(request.getInvokeType());

        RpcCommonResponse response = null;
        switch (typeEnum){
            case SYNC://同步（BIO方式）

                InvokerFuture invokerFuture = new DefaultInvokerFuture();
                try {
                    response = invokerFuture
                            .waitResponse(request.getTimeout(), TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case ONEWAY://单程
                //这里类型于java里的void处理方式,不需要告诉我结果，收到请求就行了（用于服务端耗时操作,但是客户端不关心操作结果）
                break;
            case FUTURE://future
                //这里与同步类似，只是发起请求到服务端，然后客户端如果需要就阻塞等待服务端响应即可，不需要向同步那样必须等待结果给客户端。区别就是分别在不同线程里处理
            break;
            case CALLBACK://回调
                //这里与future稍微有点区别，回调是客户端与服务端都不阻塞（AIO方式），future是同步非阻塞（NIO方式）
                break;
                default:
                    throw new Error("不支持的调用类型",new RuntimeException("只支持:"+InvokeTypeEnum.values()+"这几种调用类型！"));
        }
        return (PussycatResponse)response;
    }
}
