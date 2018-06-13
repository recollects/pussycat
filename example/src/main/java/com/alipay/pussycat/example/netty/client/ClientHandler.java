package com.alipay.pussycat.example.netty.client;

import com.alipay.pussycat.example.netty.model.NettyResponse;
import com.alipay.pussycat.example.netty.result.RpcContextResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:39
 */
public class ClientHandler extends SimpleChannelInboundHandler<NettyResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive 客戶端");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyResponse msg) throws Exception {

        if (msg instanceof NettyResponse) {
            System.out.println("...........");
            NettyResponse response = (NettyResponse) msg;
            System.out.println("接收响应信息:" + response.getData());
            response.setData("响应内容!!!");
            RpcContextResult.getContextResponse().put(response.getRequestId(), response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
