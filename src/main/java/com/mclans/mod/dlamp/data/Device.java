package com.mclans.mod.dlamp.data;

import com.mclans.mod.dlamp.protocol.DataType;
import com.mclans.mod.dlamp.protocol.packet.DataPointChangePacket;
import com.mclans.mod.dlamp.protocol.packet.PingPacket;
import com.mclans.mod.dlamp.protocol.packet.PowerSwitchPacket;
import com.mclans.mod.dlamp.runnable.DeviceConnector;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Device {
    private DataPoint dataPoint;
    public boolean isPowerOn;
//    private String deviceId;  // deviceId仅限设备和插件做校验，不对MOD开放
    private int firmwareVersion;
    private String mac;
    private String productKey;
    private String ip;
    private DeviceConnector connector;
    private ChannelHandlerContext ctx;
    public Device(String mac, String ip) {
        this.mac = mac;
        this.ip = ip;
        this.connector = new DeviceConnector(this);
    }
    public String getIp() {
        return this.ip;
    }

    public void connect() {
        this.connector.connect();
    }
    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
    public ChannelHandlerContext getCtx() {
        return ctx;
    }
    public String getMac() {
        return this.mac;
    }
    public DataPoint getDataPoint() {
        return dataPoint;
    }
    public void setDataPoint(DataPoint dataPoint) {
        this.dataPoint = dataPoint;
    }
    public ScheduledFuture startPingService() {
        return ctx.executor().scheduleAtFixedRate(() -> ctx.writeAndFlush(new PingPacket()),  0, 5, TimeUnit.SECONDS);
    }
    public void setPower(boolean isPowerOn) {
        this.isPowerOn = isPowerOn;
        ctx.writeAndFlush(new PowerSwitchPacket(isPowerOn));
    }
    public boolean switchPower() {
        this.isPowerOn = !this.isPowerOn;
        ctx.writeAndFlush(new PowerSwitchPacket(this.isPowerOn));
        return this.isPowerOn;
    }
    public void controlDevice(DataPoint dataPoint) {
        DataPointChangePacket dataPointChangePacket = new DataPointChangePacket(DataType.CONTROL, dataPoint);
        ctx.writeAndFlush(dataPointChangePacket);
    }
    public void alertDevice(DataPoint dataPoint) {
        DataPointChangePacket dataPointChangePacket = new DataPointChangePacket(DataType.ALERT, dataPoint);
        ctx.writeAndFlush(dataPointChangePacket);
    }
    public void clearControl() {
        DataPointChangePacket dataPointChangePacket = new DataPointChangePacket(DataType.CLEAR_CONTROL);
        ctx.writeAndFlush(dataPointChangePacket);
    }
    public void clearAlert() {
        DataPointChangePacket dataPointChangePacket = new DataPointChangePacket(DataType.CLEAR_ALERT);
        ctx.writeAndFlush(dataPointChangePacket);
    }
}
