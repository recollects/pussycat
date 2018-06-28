package com.alipay.pussycat.example.netty.client;

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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月21日 下午6:38
 */
public class PussycatClient {

    private static int ioWorkerCount       = Runtime.getRuntime().availableProcessors() * 2;
    private static int executorThreadCount = 16;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        NettyRequest request = new NettyRequest();
        request.setData("叶家东你好啊.");
        request.setRequestId(UUID.randomUUID().toString());
        ClientHandler clientHandler = new ClientHandler();

        Thread thread = new Thread() {
            @Override
            public void run() {
                syncRequest(request, clientHandler);
            }
        };

        thread.setDaemon(true);
        thread.start();
        //        countDownLatch.await(3000, TimeUnit.MILLISECONDS);
        //        NettyResponse nettyResponse = RpcContextResult.getContextResponse().get(request.getRequestId());

        SyncFuture syncFuture = new SyncFuture();
        NettyResponse nettyResponse = (NettyResponse) syncFuture.get();

        System.out.println("同步调用结果--->" + nettyResponse);

        System.out.println("主线程响应信息:" + nettyResponse.getData());

    }

    private static void syncRequest(NettyRequest request, ClientHandler clientHandler) {
        EventLoopGroup workerGroup = new NioEventLoopGroup(ioWorkerCount);
        EventLoopGroup eventExecutor = new NioEventLoopGroup(executorThreadCount);

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
                                clientHandler);

                    }
                });

        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8081).sync();

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

            //            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
