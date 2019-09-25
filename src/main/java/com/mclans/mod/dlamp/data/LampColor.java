package com.mclans.mod.dlamp.data;


import io.netty.buffer.ByteBuf;

public class LampColor {

    private boolean randomColor;
    private byte colorRed;
    private byte colorGreen;
    private byte colorBlue;
    private int keepFrames;

    public LampColor(ByteBuf buf) {
        randomColor = buf.readBoolean();
        colorRed = buf.readByte();
        colorGreen = buf.readByte();
        colorBlue = buf.readByte();
        keepFrames = buf.readInt();
    }

    public boolean isRandomColor() {
        return this.randomColor;
    }

    public void setRandomColor(boolean b) {
        this.randomColor = b;
    }

    public byte getColorRed() {
        return this.colorRed;
    }

    public void setColorRed(byte colorRed) {
        this.colorRed = colorRed;
    }

    public byte getColorGreen() {
        return colorGreen;
    }

    public void setColorGreen(byte colorGreen) {
        this.colorGreen = colorGreen;
    }

    public byte getColorBlue() {
        return colorBlue;
    }

    public void setColorBlue(byte colorBlue) {
        this.colorBlue = colorBlue;
    }

    public int getKeepFrames() {
        return this.keepFrames;
    }

    public void setKeepFrames(int keepFrames) {
        this.keepFrames = keepFrames;
    }

    public void writeBuf(ByteBuf buf) {
        buf.writeByte(randomColor ? 1 : 0);
        buf.writeByte(colorRed);
        buf.writeByte(colorGreen);
        buf.writeByte(colorBlue);
        buf.writeInt(keepFrames);
    }
}
