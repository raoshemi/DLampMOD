package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class HandshakePacket extends Packet {
    @Override
    public void write(ByteBuf buf) {
        buf.writeBytes(Protocol.Flag.HANDSHAKE.getFlag());
    }
}
