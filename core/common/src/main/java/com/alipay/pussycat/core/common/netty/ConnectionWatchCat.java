package com.alipay.pussycat.core.common.netty;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wb-smj330392
 * @create 2018-07-04 17:24
 */
public class ConnectionWatchCat extends ChannelInboundHandlerAdapter implements TimerTask {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionWatchCat.class);

    private Bootstrap bootstrap;

    private ChannelHandlersContainer channelHandlersContainer;

    private volatile SocketAddress remoteAddress;

    private Timer timer = new HashedWheelTimer();

    private boolean reconnect = true;

    private long delay = 30L;

    private int totalConnectCount = 3;

    private int connectAttamtp = 0;

    public ConnectionWatchCat(Bootstrap bootstrap,ChannelHandlersContainer channelHandlersContainer) {
        this.bootstrap = bootstrap;
        this.channelHandlersContainer = channelHandlersContainer;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        logger.info("开始进行重连...");
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(channelHandlersContainer.handlers());
            }
        });
        ChannelFuture future = bootstrap.connect(remoteAddress);

        future.addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                boolean success = future.isSuccess();
                if (!success){
                    future.channel().pipeline().fireChannelInactive();
                }
            }
        });

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接后重置为0
        connectAttamtp = 0;
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("当前channel inactive");
        remoteAddress = ctx.channel().remoteAddress();
        if (reconnect){
            if (connectAttamtp < totalConnectCount){
                connectAttamtp++;

                logger.info("将进行第 [{}] 次重连",connectAttamtp);
                timer.newTimeout(this,delay, TimeUnit.MILLISECONDS);
            }
        }
        super.channelInactive(ctx);
    }
}
