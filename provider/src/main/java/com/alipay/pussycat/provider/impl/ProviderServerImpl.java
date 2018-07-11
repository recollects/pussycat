package com.alipay.pussycat.provider.impl;

import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.PussycatContants;
import com.alipay.pussycat.core.common.model.RemotingTransporter;
import com.alipay.pussycat.core.common.model.ServiceMetadata;
import com.alipay.pussycat.core.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.provider.DefaultProvider;
import com.alipay.pussycat.provider.ProviderServer;
import com.alipay.pussycat.provider.handler.PYCServerHandler;
import com.alipay.pussycat.register.redis.ServerRegisterService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * RPC 提供方实现
 *
 * @author wb-smj330392
 */
public class ProviderServerImpl implements ProviderServer {

    private static final Logger logger = LoggerFactory.getLogger(ProviderServerImpl.class);

    private static AtomicBoolean started = new AtomicBoolean(false);

    private ServerRegisterService serverRegisterService = PussycatServiceContainer
            .getInstance(ServerRegisterService.class);

    EventLoopGroup bossGroup   = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

    private ChannelFuture future;


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
        bind();
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
        System.out.println("..STOP..");

        try {
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        } catch (Exception e) {

        }

        //TODO 调用connection#onClose方法，响应所有已请求的过来调用


    }

    /**
     *
     */
    private synchronized void bind() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 可配置的几个
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new ObjectEncoder(),
                                new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)),
                                new PYCServerHandler());
                    }
                });

        bootstrap.bind(PussycatContants.DEFAULT_PORT).syncUninterruptibly();
        System.out.println("服务端启动成功,服务端口号:" + PussycatContants.DEFAULT_PORT);
        started.compareAndSet(false, true);
    }

    /**
     * 2.0 版本服务端等待与消费端连接，基于注解，只需一次启动
     * @throws PussycatException
     */
    @Override
    public void serverStart() throws PussycatException{


    }


}
