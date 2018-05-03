package com.alipay.pussycat.server;

import java.util.concurrent.atomic.AtomicBoolean;

import com.alipay.pussycat.common.model.PussycatContants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 该类开启pussycat服务端网络监听。
 *
 * @author kongming.lrq
 */
public class PussycatProviderServer implements ProviderServer {

    static private AtomicBoolean started = new AtomicBoolean(false);

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()*2);

    @Override
    public boolean isStarted() {
        return started.get();
    }

    private int port = PussycatContants.ServerPort;

    @Override
    synchronized public void startPYCServer() throws Exception {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 可配置的几个
            bootstrap.group(bossGroup, workerGroup)//
                .channel(NioServerSocketChannel.class)//
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new PYCServerInitializer());

            try {
                //绑定端口，同步等待成功
                ChannelFuture f = bootstrap.bind(port).sync();

                //等待服务端监听端口关闭
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {

            } finally {

            }
            started.compareAndSet(false,true);
        } catch (Exception e) {
            throw new Exception("启动HSF服务器端错误", e);
        }
    }

    @Override
    public void stopPYCServer() throws Exception {

    }




}
