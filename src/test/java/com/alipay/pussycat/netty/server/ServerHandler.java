package com.alipay.pussycat.netty.server;

import com.alipay.pussycat.netty.model.NettyRequest;
import com.alipay.pussycat.netty.model.NettyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:14
 */
public class ServerHandler extends SimpleChannelInboundHandler {

    /**
     * 这里在父类里已经调用了channelRead,所以不用重写,也不需要自己手动去释放,只用关心数据读取处理即可
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
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
        //        ByteBuf buf = (ByteBuf) msg;
        //        byte[] data = new byte[buf.readableBytes()];
        //        buf.readBytes(data);
        //        System.out.println(new String(data, CharsetUtil.UTF_8));
        //        ctx.writeAndFlush(Unpooled.copiedBuffer("苏明瑾你好呀!".getBytes()));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
