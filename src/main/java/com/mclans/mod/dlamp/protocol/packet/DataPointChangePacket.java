package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.data.DataPoint;
import com.mclans.mod.dlamp.protocol.DataType;
import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

public class DataPointChangePacket extends Packet {
    private DataType dataType;
    private DataPoint dataPoint;
    public DataPointChangePacket(DataType dataType) {
        this.packetName = "DataPointChangePacket";
        this.dataType = dataType;
    }
    public DataPointChangePacket(DataType dataType, DataPoint dataPoint) {
        this.packetName = "DataPointChangePacket";
        this.dataType = dataType;
        this.dataPoint = dataPoint;
    }
    public DataType getDataType() {
        return dataType;
    }
    public DataPoint getDataPoint() {
        return dataPoint;
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
        if(dataPoint != null) {
            dataPoint.writeBuf(buf);
        }
    }
}
