package com.alipay.pussycat.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wb-smj330392
 * @create 2018-04-20 14:31
 */
public class PYCServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new PYCServerHandler());
    }
}
