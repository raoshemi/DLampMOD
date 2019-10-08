package com.mclans.mod.dlamp.protocol.packet.plugin;

import com.mclans.mod.dlamp.data.RemoteInfo;
import com.mclans.mod.dlamp.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class RemoteResponsePacket extends Packet {
    private String deviceIp;
    private RemoteInfo remoteInfo;
    public void read(ByteBuf buf) {
        try {
            int len = buf.readShort();
            deviceIp = Packet.readString(buf, len);
            len = buf.readShort();
            String remoteIp = Packet.readString(buf, len);
            int port = buf.readShort();
            len = buf.readShort();
            String token = Packet.readString(buf, len);
            remoteInfo = new RemoteInfo(remoteIp, port, token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public RemoteInfo getRemoteInfo() {
        return remoteInfo;
    }

    public String getDeviceIp() {
        return deviceIp;
    }
}

