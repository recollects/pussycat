package com.alipay.pussycat.server;

import java.util.List;

import com.alipay.pussycat.common.exception.PussycatException;
import com.alipay.pussycat.common.exception.PussycatExceptionEnum;
import com.alipay.pussycat.common.model.ApplicationModel;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.common.utils.StringUtils;
import com.alipay.pussycat.consumer.model.PussycatRequest;
import com.alipay.pussycat.server.model.ProviderMethodModel;
import com.alipay.pussycat.server.model.PussycatResponse;
import com.alipay.pussycat.server.model.SimpleServiceProviderModel;
import com.alipay.pussycat.transport.model.RemotingTransporter;
import com.alipay.pussycat.transport.model.TransportBody;
import com.alipay.pussycat.transport.model.TransportProtocal;
import com.alipay.pussycat.transport.netty.serializable.Serializer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author wb-smj330392
 * @create 2018-04-20 15:59
 */
public class PYCServerHandler extends SimpleChannelInboundHandler<RemotingTransporter> {

    Serializer serializer = PussycatServiceContainer.getInstance(Serializer.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemotingTransporter msg) throws Exception {
        if (msg.getCode()== TransportProtocal.REQUEST_CODE){
            PussycatRequest pussycatRequest = (PussycatRequest)msg.getTransportBody();
            String invokeMethodName = pussycatRequest.getInvokeMethodName();
            Class<?>[] argTypes = pussycatRequest.getArgTypes();
            Object[] reqArgs = pussycatRequest.getReqArgs();
            String targetInterfaceName = pussycatRequest.getTargetInterfaceName();
            SimpleServiceProviderModel providedServiceModel = ApplicationModel.instance().getProvidedServiceModel(targetInterfaceName);

            Object result = null;
            if (providedServiceModel !=null){
                ProviderMethodModel providerMethodModel = providedServiceModel.getProviderMethodModels().get(invokeMethodName);
                if(!StringUtils.parameterTypesToStr(argTypes).equals(providerMethodModel.getMethodArgTypesJoiner())){
                    throw new PussycatException(PussycatExceptionEnum.E_10006);
                }
                result  = providerMethodModel.getMethod().invoke(providedServiceModel.getServiceInstance(), reqArgs);
            }

            PussycatResponse pussycatResponse = new PussycatResponse();
            pussycatResponse.setResult(pussycatResponse);
            pussycatResponse.setSuccess(true);
            pussycatResponse.setRequestId(msg.getRequestId());

            ctx.writeAndFlush(pussycatResponse);

        }
    }
}
