package com.alipay.pussycat.core.common.netty.coder;

import com.alipay.pussycat.core.common.model.RemotingTransporter;
import com.alipay.pussycat.core.common.model.TransportBody;
import com.alipay.pussycat.core.common.model.TransportProtocal;
import com.alipay.pussycat.core.common.netty.serializable.Serializer;
import com.alipay.pussycat.core.common.utils.PussycatServiceContainer;
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
        byte[] bodyBytes = serializer.writeObject(transportBody);
        ///协议头 标记
        out.writeShort(TransportProtocal.CAFE)
            //请求or响应
                .writeByte(msg.getTransportType())
            //业务主题类型：注册服务，订阅服务，rpc请求等
                .writeByte(msg.getCode())
            //请求的id
                .writeLong(msg.getRequestId())
            //body的字节长度
                .writeInt(bodyBytes.length)
            //body字节体
                .writeBytes(bodyBytes);
    }
}
