package com.alipay.pussycat.consumer.handler;

import com.alipay.pussycat.consumer.future.DefaultInvokerFuture;
import com.alipay.pussycat.consumer.future.InvokerFuture;
import com.alipay.pussycat.consumer.remoting.Connection;
import com.alipay.pussycat.core.common.model.PussycatResponse;
import com.alipay.pussycat.core.common.utils.DateUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月29日 下午4:21
 */
public class PYCConsumerHandler extends SimpleChannelInboundHandler<PussycatResponse> {

    /**
     * TODO 可以动态修改
     */
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive......." + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PussycatResponse response) throws Exception {

        executor.submit(new ProcessTask(ctx, response));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    static class ProcessTask implements Runnable {

        ChannelHandlerContext ctx;
        PussycatResponse      response;

        public ProcessTask(ChannelHandlerContext ctx, PussycatResponse response) {
            this.ctx = ctx;
            this.response = response;
        }

        @Override
        public void run() {
            //删除map里的future
            long start = DateUtils.now();

            Connection connection = new Connection(ctx.channel());
            //TODO
            connection.removeInvokerFuture(Integer.parseInt(response.getRequestId() + ""));

            //响应到请求端
            InvokerFuture<PussycatResponse> invokerFuture = new DefaultInvokerFuture<>();
            invokerFuture.putResponse(response);
        }
    }

    public static void main(String[] args) throws Exception {

        io.netty.util.Timer INSTANCE = new HashedWheelTimer(10, TimeUnit.MILLISECONDS);

        Timeout timeout = INSTANCE.newTimeout(new io.netty.util.TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {


            }
        }, 3000, TimeUnit.MILLISECONDS);

        //future、callback模式都属于非单线程调用，需要提供一种可以结束任务执行方式
        timeout.task();
        timeout.cancel();


    }

}
