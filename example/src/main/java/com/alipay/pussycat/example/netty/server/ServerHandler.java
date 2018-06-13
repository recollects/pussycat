package com.alipay.pussycat.example.netty.server;

import com.alipay.pussycat.example.netty.model.NettyRequest;
import com.alipay.pussycat.example.netty.model.NettyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:14
 */
public class ServerHandler extends SimpleChannelInboundHandler<NettyRequest> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyRequest msg) throws Exception {
        System.out.println("server:" + msg);
        if (msg instanceof NettyRequest) {
            System.out.println("..........");

            NettyRequest request = (NettyRequest) msg;
            System.out.println("服务端接收请求:" + request.getData());
            NettyResponse response = new NettyResponse();
            response.setData("来自服务端的响应!!!");
            response.setRequestId(request.getRequestId());
            ctx.writeAndFlush(response);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
