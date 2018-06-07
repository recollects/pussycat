package com.alipay.pussycat.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:12
 */
public class PussycatServer {
    public static void main(String[] args) {

        new Thread() {
            @Override
            public void run() {
                try {
                    new PussycatServer().start("127.0.0.1", 8081);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * @param ip
     * @param port
     * @throws Exception
     */
    public void start(String ip, int port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap b = new ServerBootstrap();

            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//设置TCP缓冲区
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)//设置发送数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)//设置接受数据缓冲大小

                    .childOption(ChannelOption.SO_KEEPALIVE, true)//保持连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder(),
                                    new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)),
                                    new ServerHandler());
                        }
                    });

            ChannelFuture f = serverBootstrap.bind(ip, port).sync();
            System.out.println("服务端启动, port:" + port);
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
