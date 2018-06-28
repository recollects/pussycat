package com.alipay.pussycat.consumer.proxy;

import com.alipay.pussycat.consumer.future.DefaultInvokerFuture;
import com.alipay.pussycat.consumer.future.InvokerFuture;
import com.alipay.pussycat.consumer.handler.PYCConsumerHandler;
import com.alipay.pussycat.consumer.remoting.Connection;
import com.alipay.pussycat.core.common.model.*;
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
import java.util.concurrent.CountDownLatch;
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

    ChannelFuture contextChannelFuture = null;

    private Object serviceProxy;

    private ServiceMetadata metadata;

    private AtomicBoolean isConn = new AtomicBoolean(false);

    public static CountDownLatch countDownLatch = new CountDownLatch(1);

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

        if (!isConn.get()) {
            connect(host, port, request);
            Thread.sleep(10);
        }

        Connection connection = new Connection(contextChannelFuture.channel());

        InvokerFuture invokerFuture = new DefaultInvokerFuture();

        connection.addInvokerFuture(invokerFuture);

        writeAndFlush(connection, host, port, request);

        //等待服务端响应过来
        RpcCommonResponse pussycatResponse = invokerFuture.waitResponse();

        //        PussycatResponse pussycatResponse = RpcContextResult.getResultMap().get(request.getRequestId());
        if (pussycatResponse == null) {
            //            RpcContextResult.getResultMap().remove(request.getRequestId());
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
    public void connect(String host, int port, PussycatRequest request) {

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
            contextChannelFuture = bootstrap.connect(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("连接服务异常" + e);
        }
    }

    /**
     *
     * @param request
     */
    private void writeAndFlush(final Connection connection, String host, int port, PussycatRequest request) {

        //        try {
        ChannelFuture channelFuture = connection.getChannel().writeAndFlush(request).addListener(

                new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            //给出错误请求响应
                            SocketChannel socketChannel = (SocketChannel) future.channel();
                        } else {
                            future.channel().eventLoop().schedule(() -> {
                                System.out.println("等待重连");
                                connect(host, port, request);
                                writeAndFlush(connection, host, port, request);
                            }, 3, TimeUnit.SECONDS);
                        }
                    }
                });

        //            contextChannelFuture.channel().closeFuture().sync();
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        } finally {
        //            workerGroup.shutdownGracefully();
        //        }
    }

    public static CountDownLatch getCountDownLatch() {
        return countDownLatch;
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
