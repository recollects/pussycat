package com.alipay.pussycat.core.common.netty.transport;

import com.alipay.pussycat.core.common.model.RemotingTransporter;
import com.alipay.pussycat.core.common.netty.ChannelHandlersContainer;
import com.alipay.pussycat.core.common.netty.ConnectionWatchCat;
import com.alipay.pussycat.core.common.netty.TransportDecoder;
import com.alipay.pussycat.core.common.netty.coder.TransportEncoder;
import com.alipay.pussycat.core.common.netty.heartCheck.IdleEventTrigger;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultMessageSizeEstimator;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.alipay.pussycat.core.common.model.TransportProtocal.REQUEST_REMOTING;
import static com.alipay.pussycat.core.common.model.TransportProtocal.RESPONSE_REMOTING;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 网络传输客户端的总控制器 服务提供者向注册中心发布服务时属于客户端
 *
 * @author wb-smj330392
 * @create 2018-07-05 11:00
 */
public class NettyTransportClientController extends NettyTransportBase{

    private static final Logger logger = LoggerFactory.getLogger(NettyTransportClientController.class);

    EventLoopGroup bossGroup   = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);


    private ChannelHandlersContainer channelHandlersContainer;



    public void nettyHandlerReady(){
        bootstrap.group(workerGroup).channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT).option(ChannelOption.MESSAGE_SIZE_ESTIMATOR, DefaultMessageSizeEstimator.DEFAULT)
            .option(ChannelOption.SO_REUSEADDR, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) SECONDS.toMillis(3));

        bootstrap.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.ALLOW_HALF_CLOSURE, false);

        ConnectionWatchCat connectionWatchCat = new ConnectionWatchCat(bootstrap,channelHandlersContainer);
        channelHandlersContainer = new ChannelHandlersContainer() {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                    connectionWatchCat,
                    new TransportDecoder(),
                    new TransportEncoder(),
                    new IdleStateHandler(0,30,0),
                    new IdleEventTrigger(),
                    new NettyTransportClientHandler()
                };
            }
        };

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(channelHandlersContainer.handlers());
            }
        });

    }




    class NettyTransportClientHandler extends SimpleChannelInboundHandler<RemotingTransporter>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingTransporter msg) throws Exception {
            processMsgReceive(ctx,msg);
        }
    }


    /**
     * channelRead0 的统一方法入口,作为server端和client端的处理方式不同
     * @param ctx
     * @param msg
     */
    private void processMsgReceive(ChannelHandlerContext ctx, RemotingTransporter msg) {

        switch (msg.getTransportType()) {
            //作为server端 client端的请求的对应的处理
            case REQUEST_REMOTING:
                //processRemotingRequest(ctx, msg);
                break;
            //作为客户端，来自server端的响应的处理
            case RESPONSE_REMOTING:
                processRemotingResponse(ctx, msg);
                break;
            default:
                break;
        }
    }

}
