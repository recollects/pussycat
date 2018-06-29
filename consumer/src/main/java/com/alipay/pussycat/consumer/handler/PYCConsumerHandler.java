package com.alipay.pussycat.consumer.handler;

import com.alipay.pussycat.consumer.future.DefaultInvokerFuture;
import com.alipay.pussycat.consumer.future.InvokerFuture;
import com.alipay.pussycat.consumer.remoting.Connection;
import com.alipay.pussycat.core.common.model.PussycatResponse;
import com.alipay.pussycat.core.common.utils.DateUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

            Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                }
            };
//            timer.sch

            Connection connection = new Connection(ctx.channel());
            //TODO
            connection.removeInvokerFuture(Integer.parseInt(response.getRequestId() + ""));

            //响应到请求端
            InvokerFuture<PussycatResponse> invokerFuture = new DefaultInvokerFuture<>();
            invokerFuture.putResponse(response);
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行");
                try {
                    Thread.sleep(4000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(task,0L,3000);

    }

}
