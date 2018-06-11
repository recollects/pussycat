package com.alipay.pussycat.core.common.netty;

import java.util.List;

import com.alipay.pussycat.transport.model.RemotingTransporter;
import com.alipay.pussycat.transport.model.TransportProtocal;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.alipay.pussycat.transport.model.TransportProtocal.CAFE;

/**
 * 解码器
 * @author wb-smj330392
 * @create 2018-05-25 20:22
 */
public class TransportDecoder extends ReplayingDecoder<TransportDecoder.state> {

    private static final Logger logger = LoggerFactory.getLogger(TransportDecoder.class);

    private static final int MAX_BODY_SIZE = 1024 * 1024 * 5;

    public TransportDecoder() {
        //设置(下文#state()的默认返回对象)
        super(state.HEADER_CAFE);
    }

    TransportProtocal protocal = new TransportProtocal();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case HEADER_CAFE:
                checkCafe(in.readShort()); // CAFE
                checkpoint(state.HEADER_TYPE);
            case HEADER_TYPE:          // 消息类型标志位
                protocal.setCode(in.readByte());
                checkpoint(state.HEADER_ID);

            case HEADER_ID:
                protocal.setId(in.readLong()); // 消息id
                checkpoint(state.HEADER_BODY_LENGTH);
            case HEADER_BODY_LENGTH:
                protocal.setBodyLength(in.readInt()); // 消息体长度
                checkpoint(state.BODY);
            case BODY:
                int bodyLength = checkBodyLength(protocal.getBodyLength());
                byte[] bytes = new byte[bodyLength];
                in.readBytes(bytes);

                out.add(RemotingTransporter.newInstance(protocal.getId(), protocal.getCode(), bytes));
                break;
            default:
                break;
        }
        checkpoint(state.HEADER_CAFE);
    }

    private int checkBodyLength(int bodyLength) throws Exception {
        if (bodyLength > MAX_BODY_SIZE) {
            throw new Exception("body of request is bigger than limit value " + MAX_BODY_SIZE);
        }
        return bodyLength;
    }

    private void checkCafe(short cafe) throws Exception {
        if (CAFE != cafe) {
            logger.error("cafe is not match");
            throw new Exception("magic value is not equal " + cafe);
        }
    }

    enum state {
        HEADER_CAFE, HEADER_TYPE, HEADER_ID, HEADER_BODY_LENGTH, BODY
    }
}
