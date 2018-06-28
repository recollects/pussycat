package com.alipay.pussycat.consumer.handler;

import com.alipay.pussycat.consumer.future.DefaultInvokerFuture;
import com.alipay.pussycat.consumer.future.InvokerFuture;
import com.alipay.pussycat.consumer.remoting.Connection;
import com.alipay.pussycat.core.common.model.PussycatResponse;
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
//        System.out.println("channelRead0:" + response);
//        RpcContextResult.getResultMap().put(response.getRequestId(), response);
//        System.out.println("resultMap:" + RpcContextResult.getResultMap());

        //删除map里的future
        Connection connection = new Connection(ctx.channel());
        //TODO
        connection.removeInvokerFuture(Integer.parseInt(response.getRequestId()+""));

        //响应到请求端
        InvokerFuture<PussycatResponse> invokerFuture=new DefaultInvokerFuture<>();
        invokerFuture.putResponse(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
