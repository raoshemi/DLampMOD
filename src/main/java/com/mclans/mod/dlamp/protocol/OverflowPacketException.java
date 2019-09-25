package com.mclans.mod.dlamp.protocol;

public class OverflowPacketException
        extends RuntimeException {
    public OverflowPacketException(String message) {
        super(message);
    }
}