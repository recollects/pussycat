package com.alipay.pussycat.register.redis.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.alipay.pussycat.common.model.ApplicationModel;
import com.alipay.pussycat.consumer.model.PussycatRequest;
import com.alipay.pussycat.consumer.model.SimpleServiceConsumerModel;
import com.alipay.pussycat.register.redis.service.CacheManager;
import com.alipay.pussycat.register.redis.service.RedisCacheManagerImpl;
import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.model.Result;
import com.alipay.pussycat.common.utils.LogDef;
import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.server.model.ProviderMethodModel;
import com.alipay.pussycat.server.model.ServiceMetadata;
import com.alipay.pussycat.server.model.SimpleServiceProviderModel;
import com.alipay.pussycat.register.ServerRegisterService;
import com.alipay.pussycat.server.ProviderServer;
import com.alipay.pussycat.transport.model.RemotingTransporter;
import com.alipay.pussycat.transport.model.TransportItemBytes;
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
import org.slf4j.Logger;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月25日 下午5:38
 */
public class ServerRegisterServiceImpl implements ServerRegisterService {

    protected static final Logger logger_publish = LogDef.SERVICE_PUBLISH_DIGEST;

    private CacheManager cacheManager = PussycatServiceContainer.getInstance(CacheManager.class);
    private ProviderServer providerServer = PussycatServiceContainer.getInstance(ProviderServer.class);



    /**
     * 注册服务[将提供服方的接口信息,存到注册中心去]
     *
     * @param metadata
     * @return
     */
    @Override
    public boolean register(ServiceMetadata metadata) {

        // 将提供方服务存到注册中心去
        SimpleServiceProviderModel simpleServiceProviderModel = new SimpleServiceProviderModel(metadata);

        String key = PussycatContants.PUSSYCAT_REDIS_KEY_PRE + metadata.getUniqueName();
        return cacheManager.set(key, simpleServiceProviderModel);
    }

    @Override
    public boolean subscribe(ServiceMetadata metadata) {
        try {
            //获取注册中心服务信息
            String key = PussycatContants.PUSSYCAT_REDIS_KEY_PRE + metadata.getUniqueName();
            SimpleServiceProviderModel simpleServiceProviderModel  = (SimpleServiceProviderModel)cacheManager.get((Object)key);
            //更新至本地缓存信息
            Map<String, ProviderMethodModel> providerMethodModels = simpleServiceProviderModel.getProviderMethodModels();
            ApplicationModel.instance().getProviders().putIfAbsent(simpleServiceProviderModel.getServiceName(),simpleServiceProviderModel);
        }catch (Exception e){
            return false;
        }


        return true;
    }

    @Override
    public Result<Boolean> update(ServiceMetadata event) {
        return new Result(false);
    }

    @Override
    public Result<Boolean> remove(ServiceMetadata event) {
        return new Result(false);
    }

    /**
     * 发起远程调用
     * @param pussycatRequest
     */
    public void call(PussycatRequest pussycatRequest, SimpleServiceProviderModel providedServiceModel){
        RemotingTransporter remotingTransporter = new RemotingTransporter();
        remotingTransporter.setTransportBody(pussycatRequest);
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
                ChannelFuture future = bootstrap.connect(providedServiceModel.getHost(), providedServiceModel.getPort()).sync();
                future.channel().writeAndFlush(remotingTransporter);
                future.channel().closeFuture().sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
    }


}
