package com.alipay.pussycat.consumer.remoting;

import com.alipay.pussycat.consumer.future.InvokerFuture;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 连接channel统一管理
 *
 * @author recollects
 * @date 2018年06月28日 下午4:05 
 * @version V1.0
 *
 */
public class Connection {

    private Channel channel;

    private final ConcurrentHashMap<Integer, InvokerFuture> invokeFutureMap = new ConcurrentHashMap<Integer, InvokerFuture>(
            4);

    public Connection(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public InvokerFuture getInvokerFuture(Integer id) {
        return this.invokeFutureMap.get(id);
    }

    public InvokerFuture addInvokerFuture(InvokerFuture future) {
        return this.invokeFutureMap.putIfAbsent(future.id(), future);
    }

    public InvokerFuture removeInvokerFuture(Integer id) {
        return this.invokeFutureMap.remove(id);
    }

    /**
     * 当关闭的时候需要回调一下这方法
     */
    public void onClose() {
        Iterator<Map.Entry<Integer, InvokerFuture>> iterator = invokeFutureMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, InvokerFuture> entry = iterator.next();

            iterator.remove();

            InvokerFuture future = entry.getValue();
            if (future != null) {
                //说明有正在请求的future等待，统一返回一个错误信息给客户端
                future.putResponse(future.createFailResponse());
            }
        }

    }

    /**
     * 关闭连接
     */
    public void close() {
        getChannel().close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                //
                System.out.println("关闭中...");
            }
        });

    }
}
