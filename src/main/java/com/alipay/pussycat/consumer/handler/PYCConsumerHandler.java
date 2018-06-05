package com.alipay.pussycat.consumer.handler;

import com.alipay.pussycat.common.model.RpcContextResult;
import com.alipay.pussycat.server.model.PussycatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月29日 下午4:21
 */
public class PYCConsumerHandler extends SimpleChannelInboundHandler<PussycatResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive......." + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PussycatResponse response) throws Exception {
        System.out.println("channelRead0:" + response);
        RpcContextResult.getResultMap().put(response.getRequestId(), response);
        System.out.println("resultMap:" + RpcContextResult.getResultMap());
        //        PYCServiceProxy.getCountDownLatch().countDown();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
