package com.alipay.pussycat.provider.handler;

import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.PussycatResponse;
import com.alipay.pussycat.core.common.utils.PussycatProviderContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @author wb-smj330392
 * @create 2018-04-20 15:59
 */
public class PYCServerHandler extends SimpleChannelInboundHandler<PussycatRequest> {

//    Serializer serializer = PussycatServiceContainer.getInstance(Serializer.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive...." + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PussycatRequest request) throws Exception {

        System.out.println("channelRead0..." + request);

        //        if (request.getCode() == TransportProtocal.REQUEST_CODE) {
        //            PussycatRequest pussycatRequest = (PussycatRequest) request.getTransportBody();
        //            String invokeMethodName = pussycatRequest.getInvokeMethodName();
        //            Class<?>[] argTypes = pussycatRequest.getArgTypes();
        //            Object[] reqArgs = pussycatRequest.getReqArgs();
        //            String targetInterfaceName = pussycatRequest.getTargetInterfaceName();
        //            SimpleServiceProviderModel providedServiceModel = ApplicationModel.instance().getProvidedServiceModel(targetInterfaceName);
        //
        //            Object result = null;
        //            if (providedServiceModel != null) {
        //                ProviderMethodModel providerMethodModel = providedServiceModel.getProviderMethodModels().get(invokeMethodName);
        //                if (!StringUtils.parameterTypesToStr(argTypes).equals(providerMethodModel.getMethodArgTypesJoiner())) {
        //                    throw new PussycatException(PussycatExceptionEnum.E_10006);
        //                }
        //                result = providerMethodModel.getMethod().invoke(providedServiceModel.getServiceInstance(), reqArgs);
        //            }
        //
        //            PussycatResponse pussycatResponse = new PussycatResponse();
        //            pussycatResponse.setResult(result);
        //            pussycatResponse.setSuccess(true);
        //            pussycatResponse.setRequestId(request.getRequestId());
        //
        //            ctx.writeAndFlush(pussycatResponse);
        //        }

        System.out.println("服务端正确收到客户端的请求....");
        PussycatResponse pussycatResponse = new PussycatResponse();
//        pussycatResponse.setResult("OK");
        pussycatResponse.setSuccess(true);
        pussycatResponse.setRequestId(request.getRequestId());

        String interfaceName = request.getServiceName();

        Object objRef = PussycatProviderContainer.providerObject.get(interfaceName);

        String methodName = request.getMethodName();

        Method method = objRef.getClass().getMethod(methodName, request.getArgTypes());

        Object result = method.invoke(objRef, request.getReqArgs());
        pussycatResponse.setResult(result);
        ctx.writeAndFlush(pussycatResponse);
        System.out.println("服务端响应结果:" + pussycatResponse);
    }

}
