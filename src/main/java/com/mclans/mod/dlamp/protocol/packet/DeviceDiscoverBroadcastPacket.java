package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class DeviceDiscoverBroadcastPacket extends Packet {
    public DeviceDiscoverBroadcastPacket() {
        this.packetName = "DeviceDiscoverBroadcastPacket";
    }
    public void write(ByteBuf buf)
    {
        buf.writeBytes(Protocol.Flag.DISCOVER_BROADCAST.getFlag());
    }
}
