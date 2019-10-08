package com.mclans.mod.dlamp.protocol.packet.plugin;

import com.mclans.mod.dlamp.protocol.Protocol;
import com.mclans.mod.dlamp.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PluginPacket extends Packet {

    public void write(ByteBuf buf) {
        ByteBuf out = Unpooled.buffer();
        out.writeBytes(Protocol.HEAD);
        Packet.writeVarInt(buf.readableBytes(), out);
        out.writeBytes(buf);
        buf.clear();
        buf.writeBytes(out);
    }
}
