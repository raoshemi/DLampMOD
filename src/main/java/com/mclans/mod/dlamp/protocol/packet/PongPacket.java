package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class PongPacket extends Packet {
    public PongPacket() {
        this.packetName = "PongPacket";
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeBytes(Protocol.Flag.PONG.getFlag());
    }
}
