package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.data.DataPoint;
import com.mclans.mod.dlamp.protocol.DataType;
import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class DataPointChangePacket extends Packet {
    private DataType dataType;
    private DataPoint dataPoint;
    public DataPointChangePacket() {
        this.packetName = "DataPointChangePacket";
    }

    @Override
    public void read(ByteBuf buf) {
        byte dataTypeByte = buf.readByte();
        this.dataType = DataType.getDataType(dataTypeByte);
        this.dataPoint = new DataPoint(buf);
    }
    @Override
    public void write(ByteBuf buf) {
        buf.writeBytes(Protocol.Flag.DATAPOINT_CHANGE.getFlag());
        buf.writeByte(dataType.getValue());
        dataPoint.writeBuf(buf);
    }
}
