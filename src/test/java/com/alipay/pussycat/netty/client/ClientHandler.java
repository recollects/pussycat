package com.alipay.pussycat.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:39
 */
public class ClientHandler extends SimpleChannelInboundHandler {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            ByteBuf buf = (ByteBuf) msg;
//
//            System.out.print(buf.toString(CharsetUtil.UTF_8));
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        channelRead(ctx,msg);
//        try {

        ByteBuf buf = (ByteBuf) msg;
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        System.out.println(new String(data,CharsetUtil.UTF_8));
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
