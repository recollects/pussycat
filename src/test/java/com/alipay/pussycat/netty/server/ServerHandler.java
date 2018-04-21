package com.alipay.pussycat.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:14
 */
public class ServerHandler extends SimpleChannelInboundHandler {


//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//        try {
//            ByteBuf in = (ByteBuf) msg;
//            // 打印客户端输入，传输过来的的字符
//            System.out.print(in.toString(CharsetUtil.UTF_8));
//            ctx.writeAndFlush(Unpooled.copiedBuffer("苏明瑾你好呀!".getBytes()));
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
//    }


    /**
     * 这里在父类里已经调用了channelRead,所以不用重写,也不需要自己手动去释放,只用关心数据读取处理即可
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        channelRead(ctx, msg);
//        try {
            ByteBuf buf = (ByteBuf) msg;
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            System.out.println(new String(data, CharsetUtil.UTF_8));
            ctx.writeAndFlush(Unpooled.copiedBuffer("苏明瑾你好呀!".getBytes()));
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
