package com.mclans.mod.dlamp.data;


import io.netty.buffer.ByteBuf;

public class DataPoint {
    private byte loopMode;
    private byte loopTimes;
    private byte brightness;
    private LampColor color1;
    private LampColor color2;
    private LampTransition transition1;
    private LampTransition transition2;

    public DataPoint(ByteBuf buf) {
        this.loopMode = buf.readByte();
        this.loopTimes = buf.readByte();
        this.brightness = buf.readByte();
        ByteBuf colorBuf1 = buf.readBytes(8);
        this.color1 = new LampColor(colorBuf1);
        ByteBuf colorBuf2 = buf.readBytes(8);
        this.color2 = new LampColor(colorBuf2);
        ByteBuf transBuf1 = buf.readBytes(8);
        this.transition1 = new LampTransition(transBuf1);
        ByteBuf transBuf2 = buf.readBytes(8);
        this.transition2 = new LampTransition(transBuf2);
    }

    public void writeBuf(ByteBuf buf) {
        buf.writeByte(loopMode);
        buf.writeByte(loopTimes);
        buf.writeByte(brightness);
        color1.writeBuf(buf);
        color2.writeBuf(buf);
        transition1.writeBuf(buf);
        transition2.writeBuf(buf);
    }
}
