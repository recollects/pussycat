package com.alipay.pussycat.netty.client;

import com.alipay.pussycat.netty.model.NettyResponse;
import com.alipay.pussycat.netty.result.RpcContextResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:39
 */
public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof NettyResponse) {
            System.out.println("...........");
            NettyResponse response = (NettyResponse) msg;
            System.out.println("接收响应信息:" + response.getData());
            RpcContextResult.getContextResponse().put(response.getRequestId(), response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
