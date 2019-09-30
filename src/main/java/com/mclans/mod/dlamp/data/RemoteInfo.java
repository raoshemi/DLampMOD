package com.mclans.mod.dlamp.data;

public class RemoteInfo {
    private String remoteIp;
    private int remotePort;
    private String token;
    public RemoteInfo(String remoteIp, int remotePort, String token) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
        this.token = token;
    }
    public String getRemoteIp() {
        return this.remoteIp;
    }
    public int getRemotePort() {
        return this.remotePort;
    }
    public String getToken() {
        return this.token;
    }
}
