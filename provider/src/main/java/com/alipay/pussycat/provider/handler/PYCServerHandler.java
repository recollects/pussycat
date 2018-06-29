package com.alipay.pussycat.provider.handler;

import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.PussycatResponse;
import com.alipay.pussycat.core.common.utils.PussycatProviderRefCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wb-smj330392
 * @create 2018-04-20 15:59
 */
public class PYCServerHandler extends SimpleChannelInboundHandler<PussycatRequest> {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive...." + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PussycatRequest request) throws Exception {

        System.out.println("channelRead0..." + request);

        executor.submit(new InvokerHandle(ctx, request));

    }

    /**
     * 处理请求任务
     *
     */
    static class InvokerHandle implements Runnable {
        ChannelHandlerContext ctx;
        PussycatRequest       request;

        public InvokerHandle(ChannelHandlerContext ctx, PussycatRequest request) {
            this.ctx = ctx;
            this.request = request;
        }

        @Override
        public void run() {
            try {
                System.out.println("服务端正确收到客户端的请求....");
                PussycatResponse pussycatResponse = new PussycatResponse();
                //        pussycatResponse.setResult("OK");
                pussycatResponse.setSuccess(true);
                pussycatResponse.setRequestId(request.getRequestId());

                String interfaceName = request.getServiceName();

                Object objRef = PussycatProviderRefCache.providerObject.get(interfaceName);

                String methodName = request.getMethodName();

                Method method = objRef.getClass().getMethod(methodName, request.getArgTypes());

                Object result = method.invoke(objRef, request.getReqArgs());
                pussycatResponse.setResult(result);
                ctx.writeAndFlush(pussycatResponse);
                System.out.println("服务端响应结果:" + pussycatResponse);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
