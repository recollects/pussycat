package com.alipay.pussycat.core.common.netty.heartCheck.readIdle;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳检测
 *
 * @author wb-smj330392
 * @create 2018-07-04 10:46
 */
public class HeartServerHandler extends ChannelInboundHandlerAdapter {

    private int loss_connect_count = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE){
                loss_connect_count++;
                System.out.println("No data was received for 5s");
                if (loss_connect_count > 2){
                    System.out.println("close this channel");
                    ctx.channel().close();
                }
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead..");
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
