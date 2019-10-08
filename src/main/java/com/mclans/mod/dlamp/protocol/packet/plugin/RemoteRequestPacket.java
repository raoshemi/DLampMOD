package com.mclans.mod.dlamp.protocol.packet.plugin;

import com.mclans.mod.dlamp.data.Device;
import com.mclans.mod.dlamp.protocol.Protocol;
import com.mclans.mod.dlamp.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;

// 插件通道包
public class RemoteRequestPacket extends PluginPacket {
    private Device device;
    public RemoteRequestPacket(Device device) {
        this.packetName = "RemoteRequestPacket";
        this.device = device;
    }
    public void write(ByteBuf buf) {
        buf.writeBytes(Protocol.Flag.REMOTE_REQUEST.getFlag());
        buf.writeShort(device.getIp().length());
        Packet.writeString(device.getIp(), buf);
        buf.writeShort(device.getMac().length());
        Packet.writeString(device.getMac(), buf);
        super.write(buf);
    }
}
