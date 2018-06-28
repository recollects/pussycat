package com.alipay.pussycat.example.netty.demo;

import com.alipay.pussycat.example.netty.future.SyncFuture;
import com.alipay.pussycat.example.netty.model.NettyRequest;
import com.alipay.pussycat.example.netty.model.NettyResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author recollects
 * @date 2018年06月28日 上午10:23 
 * @version V1.0
 *
 */
public class RpcClient {

    static final ConcurrentHashMap<String,SyncFuture> syncFutureMap = new ConcurrentHashMap<>(4);

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup eventExecutor = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(workerGroup)
                .option(ChannelOption.SO_BACKLOG, 128)//设置TCP缓冲区
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)//设置发送数据缓冲大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)//设置接受数据缓冲大小
                .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ObjectEncoder(),
                                new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)),
                                new ResponseHandler());

                    }
                });

        ChannelFuture future = bootstrap.connect("127.0.0.1", 9091).sync();

        NettyRequest request = new NettyRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setData("我来了！");

        future.channel().writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    //给出错误请求响应
                    SocketChannel socketChannel = (SocketChannel) future.channel();
                } else {
                    future.channel().eventLoop().schedule(() -> {
                        //等待重连
                    }, 3, TimeUnit.SECONDS);
                }
            }
        });

        SyncFuture syncFuture = new SyncFuture();
        syncFutureMap.put(request.getRequestId(),syncFuture);

        Object result = syncFuture.get();
        System.out.println("等待结束....");
        syncFutureMap.remove(request.getRequestId());
        System.out.println(result);
        //        future.channel().closeFuture().sync();

    }

    static class ResponseHandler extends SimpleChannelInboundHandler<NettyResponse>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, NettyResponse msg) throws Exception {
//            System.out.println(msg);

            SyncFuture syncFuture = syncFutureMap.get(msg.getRequestId());
            if (syncFuture!=null){
                syncFuture.setResponse(msg);
            }else {
                System.out.println("调用不存在...");
            }

        }
    }


}
