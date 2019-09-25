package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class PingPacket extends Packet {
    public PingPacket() {
        this.packetName = "PingPacket";
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeBytes(Protocol.Flag.PING.getFlag());
    }
}
