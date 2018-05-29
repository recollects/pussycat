package com.alipay.pussycat.transport.netty;

import com.alipay.pussycat.common.utils.PussycatServiceContainer;
import com.alipay.pussycat.transport.model.RemotingTransporter;
import com.alipay.pussycat.transport.model.TransportBody;
import com.alipay.pussycat.transport.model.TransportProtocal;
import com.alipay.pussycat.transport.netty.serializable.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 网络传输编码器
 *
 * @author wb-smj330392
 * @create 2018-05-25 16:30
 */
public class TransportEncoder extends MessageToByteEncoder<RemotingTransporter> {

    private Serializer serializer = PussycatServiceContainer.getInstance(Serializer.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, RemotingTransporter msg, ByteBuf out) throws Exception {
        TransportBody transportBody = msg.getTransportBody();
        byte[] bodyBytes = serializer.writeObject(transportBody);///协议头
        out.writeShort(TransportProtocal.CAFE)
            .writeByte(msg.getCode())
            .writeLong(msg.getRequestId())
            .writeLong(bodyBytes.length)
            .writeBytes(bodyBytes);
    }
}
