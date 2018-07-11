package com.alipay.pussycat.core.common.netty.heartCheck;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author wb-smj330392
 * @create 2018-07-05 14:21
 */
public class IdleEventTrigger extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("循环触发时间："+new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                    ctx.channel().writeAndFlush(Heartbeats.heartbeatContent());
                }

        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
