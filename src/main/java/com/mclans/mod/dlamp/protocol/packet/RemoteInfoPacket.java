package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.data.RemoteInfo;
import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class RemoteInfoPacket extends Packet {
    private RemoteInfo remoteInfo;
    public RemoteInfoPacket(RemoteInfo remoteInfo) {
        this.remoteInfo = remoteInfo;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeBytes(Protocol.Flag.REMOTE_INFO.getFlag());
        buf.writeShort(remoteInfo.getRemoteIp().length());
        Packet.writeString(remoteInfo.getRemoteIp(), buf);
        buf.writeInt(remoteInfo.getRemotePort());
        buf.writeShort(remoteInfo.getToken().length());
        Packet.writeString(remoteInfo.getToken(), buf);
    }
}
