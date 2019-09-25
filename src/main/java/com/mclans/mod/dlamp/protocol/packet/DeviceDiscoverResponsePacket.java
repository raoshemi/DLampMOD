package com.mclans.mod.dlamp.protocol.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.io.UnsupportedEncodingException;

public class DeviceDiscoverResponsePacket extends Packet {
    private String deviceId;
    private int firmwareVersion;
    private String mac;
    private String productKey;

    public void read(ByteBuf buf) throws UnsupportedEncodingException {

        int len = buf.readShort();
        this.deviceId = readString(buf, len);
        len = buf.readShort();
        byte[] b = new byte[len];
        buf.readBytes(b);
        this.mac = ByteBufUtil.hexDump(b);
        this.firmwareVersion = buf.readInt();
        len = buf.readShort();
        this.productKey = readString(buf, len);

    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getMac() {
        return this.mac;
    }

    public int getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public String getProductkey() {
        return this.productKey;
    }

}
