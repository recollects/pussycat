package com.alipay.pussycat.consumer.proxy;

import com.alipay.pussycat.consumer.future.DefaultInvokerFuture;
import com.alipay.pussycat.consumer.future.InvokerFuture;
import com.alipay.pussycat.consumer.handler.PYCConsumerHandler;
import com.alipay.pussycat.consumer.remoting.Connection;
import com.alipay.pussycat.core.common.enums.PussycatExceptionEnum;
import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.PussycatResponse;
import com.alipay.pussycat.core.common.model.RpcCommonResponse;
import com.alipay.pussycat.core.common.model.ServiceMetadata;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 生成服务代理对象
 *
 * @author recollects
 * @version V1.0
 * @date 2018年06月01日 下午12:14
 */
public class PYCServiceProxy implements InvocationHandler {

    private Object serviceProxy;

    private ServiceMetadata metadata;

    static final int retryCount=3;

    private AtomicBoolean isConn = new AtomicBoolean(false);

    public PYCServiceProxy(ServiceMetadata metadata) {
        this.metadata = metadata;
        Class[] interfaces = { metadata.getItfClass() };

        Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, this);
        serviceProxy = proxyInstance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String host = metadata.getHost();
        int port = metadata.getPort();

        PussycatRequest request = new PussycatRequest(metadata.getTimeout(), metadata.getInterfaceName(),
                method.getName(), args, method.getParameterTypes());
        request.setHost(metadata.getHost());

        System.out.println("准备连接服务端.....");
        Connection connection =null;

        if (!isConn.get()) {
            int retry=0;
            connection = connect(host, port, request,retry);
            Thread.sleep(10);
        }
        if (connection==null){
            throw new PussycatException(PussycatExceptionEnum.E_10008);
        }

        InvokerFuture invokerFuture = new DefaultInvokerFuture();

        connection.addInvokerFuture(invokerFuture);

        writeAndFlush(connection, host, port, request);

        //等待服务端响应过来
        RpcCommonResponse pussycatResponse = invokerFuture.waitResponse(request.getTimeout(), TimeUnit.MICROSECONDS);

        if (pussycatResponse == null) {
            //返回错误调用
            return invokerFuture.createFailResponse();
        }

        return ((PussycatResponse) pussycatResponse).getResult();
    }

    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     *
     * @param host
     * @param port
     */
    public Connection connect(String host, int port, PussycatRequest request,int retryCount) {

        Bootstrap bootstrap = new Bootstrap()
                .group(workerGroup)
                .option(ChannelOption.SO_BACKLOG, 128)//设置TCP缓冲区
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)//设置发送数据缓冲大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)//设置接受数据缓冲大小
                .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ObjectEncoder(),
                                new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)),
                                new PYCConsumerHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            retryCount++;
            return new Connection(channelFuture.channel());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("连接服务异常" + e);
            throw new PussycatException(PussycatExceptionEnum.E_10008);
        }
    }

    /**
     *
     * @param request
     */
    private void writeAndFlush(final Connection connection, String host, int port, PussycatRequest request) {

        connection.getChannel().writeAndFlush(request).addListener(

                new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            //给出错误请求响应
                            SocketChannel socketChannel = (SocketChannel) future.channel();
                        } else {
                            future.channel().eventLoop().schedule(() -> {
                                System.out.println("等待重连");
                                int retry=0;
                                if (retry>retryCount){
                                    //重试次数据超限不重试，直接抛出
                                    throw new PussycatException(PussycatExceptionEnum.E_10008);
                                }else {
                                    connect(host, port, request,retry);
                                }

                                writeAndFlush(connection, host, port, request);
                            }, 3, TimeUnit.SECONDS);
                        }
                    }
                });
    }

    public Object getServiceProxy() {
        return serviceProxy;
    }

    //    public static void main(String[] args) {
    //        ServiceMetadata metadata = new ServiceMetadata();
    //        metadata.setHost("127.0.0.1");
    //        metadata.setPort(PussycatContants.DEFAULT_PORT);
    //        metadata.setTimeout(3000);
    //        metadata.setInterfaceName("com.alipay.pussycat.consumer.proxy.UserService");
    //        metadata.setItfClass(UserService.class);
    //        PYCServiceProxy proxy = new PYCServiceProxy(metadata);
    //        UserService serviceProxy = (UserService) proxy.getServiceProxy();
    //        String login = serviceProxy.login("yjd", "smj");
    //        System.out.println("返回结果:" + login);
    //
    //    }

}
