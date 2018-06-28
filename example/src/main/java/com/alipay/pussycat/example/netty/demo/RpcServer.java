package com.alipay.pussycat.example.netty.demo;

import com.alipay.pussycat.example.netty.model.NettyRequest;
import com.alipay.pussycat.example.netty.model.NettyResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 *
 * @author recollects
 * @date 2018年06月28日 上午10:23 
 * @version V1.0
 *
 */
public class RpcServer {

    static int port = 9091;

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

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
                                new RequestHandler());
                    }
                });

        ChannelFuture f = serverBootstrap.bind("127.0.0.1", port).sync();
        System.out.println("服务端启动, port:" + port);
//        f.channel().closeFuture().sync();
    }

    /**
     *
     */
    static class RequestHandler extends SimpleChannelInboundHandler<NettyRequest>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, NettyRequest msg) throws Exception {
            NettyResponse response = new NettyResponse();
            response.setData("收到！");
            response.setRequestId(msg.getRequestId());
            ctx.writeAndFlush(response);
        }
    }

}
