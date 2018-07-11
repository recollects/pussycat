package com.alipay.pussycat.core.common;

import com.alipay.pussycat.core.common.model.RemotingTransporter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wb-smj330392
 * @create 2018-07-05 17:23
 */
public interface ProcessorCenter {

    void processRequest(ChannelHandlerContext ctx, RemotingTransporter msg);
}