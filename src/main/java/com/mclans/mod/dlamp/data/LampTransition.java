package com.mclans.mod.dlamp.data;

import io.netty.buffer.ByteBuf;

public class LampTransition {
    private float acceleration;
    private int frames;

    public LampTransition(ByteBuf buf) {
        this.acceleration = buf.readFloat();
        this.frames = buf.readInt();
    }

    public float getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public int getFrames() {
        return this.frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public void writeBuf(ByteBuf buf) {
        buf.writeFloat(acceleration);
        buf.writeInt(frames);
    }
}