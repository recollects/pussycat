package com.alipay.pussycat.provider;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wb-smj330392
 * @create 2018-04-20 14:31
 */
@Deprecated
public class PYCServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //        ch.pipeline().addLast(new TransportEncoder(), new TransportDecoder(), new PYCServerHandler());
    }
}
