package com.alipay.pussycat.server.impl;

import com.alipay.pussycat.common.exception.PussycatException;
import com.alipay.pussycat.common.exception.PussycatExceptionEnum;
import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.register.ServerRegisterService;
import com.alipay.pussycat.server.ProviderServer;
import com.alipay.pussycat.server.handler.PYCServerHandler;
import com.alipay.pussycat.common.model.ServiceMetadata;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * RPC 提供方实现
 *
 * @author wb-smj330392
 */
public class ProviderServerImpl implements ProviderServer {

    private static AtomicBoolean started = new AtomicBoolean(false);

    private ServerRegisterService serverRegisterService = PussycatServiceContainer
            .getInstance(ServerRegisterService.class);

    @Override
    public boolean isStarted() {
        return started.get();
    }

    @Override
    public void start() throws PussycatException {
        if (isStarted()) {
            //一台机器只允许启动一次.
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("bind");
                bind();
            }
        };

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void register(ServiceMetadata metadata) throws PussycatException {
        boolean register = serverRegisterService.register(metadata);
        if (register) {
            System.out.println("接口注册成功信息:" + metadata.getUniqueName());
            return;
        }
        System.out.println("接口注册失败信息:" + metadata.getUniqueName());
    }

    @Override
    public void monitor() throws PussycatException {
        throw new RuntimeException("暂时不支持");
    }

    @Override
    public void stop() throws PussycatException {
        //        throw new RuntimeException("暂时不支持");
        //TODO
        System.out.println("..STOP..");
    }

    /**
     *
     */
    private void bind() {
        started.compareAndSet(false, true);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

        ServerBootstrap bootstrap = new ServerBootstrap();
        // 可配置的几个
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        //                        ch.pipeline().addLast(new TransportEncoder(),
                        //                                new TransportDecoder(),new PYCServerHandler());
                        ch.pipeline().addLast(new ObjectEncoder(),
                                new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)),
                                new PYCServerHandler());
                    }
                });

        try {
            ChannelFuture f = bootstrap.bind(PussycatContants.DEFAULT_PORT).sync();
            System.out.println("服务端启动成功,服务端口号:" + PussycatContants.DEFAULT_PORT);
            started.compareAndSet(false, true);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            started.compareAndSet(true, false);
            throw new PussycatException(PussycatExceptionEnum.E_10007);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
