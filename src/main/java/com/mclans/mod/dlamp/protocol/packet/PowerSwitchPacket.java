package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class PowerSwitchPacket extends Packet {
    private boolean isPowerOn;
    public PowerSwitchPacket(boolean isPowerOn) {
        this.isPowerOn = isPowerOn;
    }
    @Override
    public void read(ByteBuf buf) {
        this.isPowerOn = buf.readBoolean();
    }
    @Override
    public void write(ByteBuf buf) {
        buf.writeBytes(Protocol.Flag.POWER_SWITCH.getFlag());
        buf.writeBoolean(this.isPowerOn);
    }
}
