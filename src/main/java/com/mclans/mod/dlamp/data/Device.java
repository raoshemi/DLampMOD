package com.mclans.mod.dlamp.data;

public class Device {
    private DataPoint dataPoint;
    private boolean isPowerOn;
//    private String deviceId;  // deviceId仅限设备和插件做校验，不对MOD开放
    private int firmwareVersion;
    private String mac;
    private String productKey;
    private String ip;
    public Device(String mac, String ip) {
        this.mac = mac;
        this.ip = ip;
    }
    public String getIp() {
        return this.ip;
    }
}
