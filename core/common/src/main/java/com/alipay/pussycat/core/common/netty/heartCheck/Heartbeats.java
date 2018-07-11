package com.alipay.pussycat.core.common.netty.heartCheck;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import sun.awt.CausedFocusEvent;

import static com.alipay.pussycat.core.common.model.TransportProtocal.CAFE;
import static com.alipay.pussycat.core.common.model.TransportProtocal.HEAD_LENGTH;
import static com.alipay.pussycat.core.common.model.TransportProtocal.HEARTBEAT;


/**
 *心跳传输包
 * @author wb-smj330392
 * @create 2018-07-05 14:19
 */
public class Heartbeats {

    private static final ByteBuf HEARTBEAT_BUF;

    static {
        ByteBuf buf = Unpooled.buffer(HEAD_LENGTH);
        buf.writeShort(CAFE);
        buf.writeByte(HEARTBEAT);
        buf.writeByte(0);
        buf.writeLong(0);
        buf.writeInt(0);
        HEARTBEAT_BUF = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(buf));
    }

    /**
     * Returns the shared heartbeat content.
     */
    public static ByteBuf heartbeatContent() {
        return HEARTBEAT_BUF.duplicate();
    }

}
