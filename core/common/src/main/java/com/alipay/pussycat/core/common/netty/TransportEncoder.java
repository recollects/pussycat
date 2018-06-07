package com.alipay.pussycat.core.common.netty;

/**
 * 网络传输编码器
 *
 * @author wb-smj330392
 * @create 2018-05-25 16:30
 */
//public class TransportEncoder extends MessageToByteEncoder<RemotingTransporter> {
//
////    private Serializer serializer = PussycatServiceContainer.getInstance(Serializer.class);
//
//    @Override
//    protected void encode(ChannelHandlerContext ctx, RemotingTransporter msg, ByteBuf out) throws Exception {
//        TransportBody transportBody = msg.getTransportBody();
//        byte[] bodyBytes = serializer.writeObject(transportBody);///协议头
//        out.writeShort(TransportProtocal.CAFE)
//                .writeByte(msg.getCode())
//                .writeLong(msg.getRequestId())
//                .writeLong(bodyBytes.length)
//                .writeBytes(bodyBytes);
//    }
//}
