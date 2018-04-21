package com.alipay.pussycat.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:38
 */
public class PussycatClient {

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 100; i++) {

            new Thread() {
                @Override
                public void run() {
                    EventLoopGroup workerGroup = new NioEventLoopGroup();
                    Bootstrap bootstrap = new Bootstrap()
                            .group(workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .option(ChannelOption.SO_BACKLOG, 128)//设置TCP缓冲区
                            .option(ChannelOption.SO_SNDBUF, 32 * 1024)//设置发送数据缓冲大小
                            .option(ChannelOption.SO_RCVBUF, 32 * 1024)//设置接受数据缓冲大小

                            .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });

                    try {
                        ChannelFuture future = bootstrap.connect("127.0.0.1", 8081).sync();
                        future.channel().writeAndFlush(Unpooled.copiedBuffer("叶家东你好啊.".getBytes()));
                        future.channel().closeFuture().sync();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        workerGroup.shutdownGracefully();
                    }
                }
            }.start();

        }

    }
}
