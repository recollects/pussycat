package com.alipay.pussycat.core.common.netty.transport;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alipay.pussycat.core.common.ProcessorCenter;
import com.alipay.pussycat.core.common.model.PussycatResponse;
import com.alipay.pussycat.core.common.model.RemotingTransporter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一些通用的网络传输方法
 * @author wb-smj330392
 * @create 2018-07-05 15:16
 */
public class NettyTransportBase {

    private static final Logger logger = LoggerFactory.getLogger(NettyTransportBase.class);

    protected Bootstrap bootstrap = new Bootstrap();

    public Map<SocketAddress,ChannelModel> channelModelMap = new ConcurrentHashMap<>();


    public Map<Long,PussycatResponse> responseSheet = new ConcurrentHashMap<>();




    /**<pre>
 *                                      +---------------------------+
     *                                      | Completed successfully    |
        *                                      +---------------------------+
        *                                 +---->      isDone() = true      |
        * +--------------------------+    |    |   isSuccess() = true      |
        * |        Uncompleted       |    |    +===========================+
        * +--------------------------+    |    | Completed with failure    |
        * |      isDone() = false    |    |    +---------------------------+
        * |   isSuccess() = false    |----+---->      isDone() = true      |
        * | isCancelled() = false    |    |    |       cause() = non-null  |
        * |       cause() = null     |    |    +===========================+
        * +--------------------------+    |    | Completed by cancellation |
        *                                 |    +---------------------------+
        *                                 +---->      isDone() = true      |
        *                                      | isCancelled() = true      |
        *                                      +---------------------------+
        * */

    public Channel createChannel(InetSocketAddress addr){
        ChannelModel channelModel = this.channelModelMap.get(addr);
        if (channelModel != null && channelModel.isOK()){
            return channelModel.getChannel();
        };
        if (channelModel != null){
            this.channelModelMap.remove(addr);
        }

        //TODO 在这个地方开启连接
        ChannelFuture channelFuture = bootstrap.connect(addr);
        ChannelModel channelModelNew = new ChannelModel(channelFuture);
        this.channelModelMap.put(addr,channelModelNew);

        logger.info("create healthy channel in a asychronize way...");
        if (channelFuture.awaitUninterruptibly(3000L)) {
            if (channelModelNew.isOK()) {
                logger.info("createChannel: connect remote host[{}] success, {}", addr, channelFuture.toString());
                // 返回健康的channel
                return channelModelNew.getChannel();
            } else {
                logger.warn("createChannel: connect remote host[" + addr + "] failed, " + channelFuture.toString(), channelFuture.cause());
            }
        } else {
            logger.warn("createChannel: connect remote host[{}] timeout {}ms, {}", 3000,
                channelFuture.toString());
        }

        return null;
    }

    public Channel getAndCreateChannel(final InetSocketAddress addr) throws InterruptedException {

        if (null == addr) {
            logger.warn("address is null");
            return null;
        }

        ChannelModel cw = this.channelModelMap.get(addr);
        if (cw != null && cw.isOK()) {
            return cw.getChannel();
        }

        return this.createChannel(addr);
    }


    /**
     * 作为server端 client端的请求的对应的处理
     * @param ctx
     * @param msg
     */
    public void processRemotingRequest(ChannelHandlerContext ctx, RemotingTransporter msg,ProcessorCenter processorCenter){
        processorCenter.processRequest(ctx,msg);
    }



    /**
     * 作为客户端，来自server端的响应的处理
     * @param ctx
     * @param msg
     */
    public void processRemotingResponse(ChannelHandlerContext ctx, RemotingTransporter msg){

        //先取出缓存中的response对象
        PussycatResponse pussycatResponse = responseSheet.get(msg.getRequestId());
        if (pussycatResponse != null){
            pussycatResponse.setRemotingTransporter(msg);
            pussycatResponse.putResponse(msg);
            responseSheet.remove(msg.getRequestId());
        }
    }

    /**
     * 远程调用端的具体实现
     * @param channel
     * @param request
     * @param timeoutMillis
     * @return
     */
    public RemotingTransporter remotingInvoke(final Channel channel,final RemotingTransporter request,final long timeoutMillis) throws InterruptedException {

        try{
            PussycatResponse pussycatResponse = new PussycatResponse(request.getRequestId(),timeoutMillis);
            responseSheet.put(request.getRequestId(),pussycatResponse);
            channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        pussycatResponse.setSuccess(true);
                        return;
                    }else {
                        responseSheet.remove(request.getRequestId());
                        pussycatResponse.setSuccess(false);
                        pussycatResponse.putResponse(null);
                    }

                }
            });

            RemotingTransporter remotingTransporter = pussycatResponse.waitResponse();
            return remotingTransporter;
        }finally {
            //最后都要将其移出去
            responseSheet.remove(request.getRequestId());
        }

    }

}
