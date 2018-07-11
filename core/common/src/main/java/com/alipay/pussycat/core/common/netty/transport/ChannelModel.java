package com.alipay.pussycat.core.common.netty.transport;

import java.net.InetAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;

/**
 * 复用Channel
 * @author wb-smj330392
 * @create 2018-07-06 15:01
 */
public class ChannelModel {

    private ChannelFuture channelFuture;


    public ChannelModel(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }


    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public Channel getChannel(){
        return this.channelFuture.channel();
    }

    public boolean isOK(){
        return (getChannel()!=null && getChannel().isActive());
    }

    public ChannelPipeline getPipeline(){
        return getChannel().pipeline();
    }


}
