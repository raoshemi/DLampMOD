package com.mclans.mod.dlamp.handler;

import com.mclans.mod.dlamp.DLampManager;
import com.mclans.mod.dlamp.data.Device;
import com.mclans.mod.dlamp.event.AirkissSuccessedEvent;
import com.mclans.mod.dlamp.event.DeviceConnectedEvent;
import com.mclans.mod.dlamp.event.PowerSwitchedEvent;
import com.mclans.mod.dlamp.protocol.DataType;
import com.mclans.mod.dlamp.protocol.packet.DataPointChangePacket;
import com.mclans.mod.dlamp.protocol.packet.HandshakePacket;
import com.mclans.mod.dlamp.protocol.packet.Packet;
import com.mclans.mod.dlamp.protocol.packet.PowerSwitchPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraftforge.common.MinecraftForge;

import java.net.InetSocketAddress;

public class DeviceHandler extends SimpleChannelInboundHandler<Packet> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        Device device = getDevice(ctx);
        if(device != null) {
            String packetName = msg.getPacketName();
            switch (packetName) {
                case "HandshakeSuccessPacket":
                    device.startPingService();

                    MinecraftForge.EVENT_BUS.post(new DeviceConnectedEvent(device));
                    break;
                case "PongPacket":
                    break;
                case "PowerSwitchPacket":
                    PowerSwitchPacket powerSwitchPacket = (PowerSwitchPacket) msg;
                    device.isPowerOn = powerSwitchPacket.isPowerOn;

                    MinecraftForge.EVENT_BUS.post(new PowerSwitchedEvent(device));
                    break;
                case "DataPointChangePacket":
                    // 目前用不到，主要需求是MOD发送设置包给设备，设备没有其他设置方式的需求。
                    DataPointChangePacket dataPointChangePacket = (DataPointChangePacket) msg;
                    if (dataPointChangePacket.getDataType().equals(DataType.SETUP)) {
                        device.setDataPoint(dataPointChangePacket.getDataPoint());
                    }
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String ip = getIp(ctx);
        if(DLampManager.getDeviceMap().containsKey(ip)) {
            Device device = DLampManager.getDeviceMap().get(ip);
            System.out.println("device: " + device.getMac() + " from " + ip + " connected!");
            device.setCtx(ctx);
            ctx.writeAndFlush(new HandshakePacket());
        }

    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.fireChannelInactive();

        // TODO: 调用DeviceConnector的connect()方法，进行断线重连


    }
    private String getIp(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return inetSocketAddress.getAddress().getHostAddress();
    }
    private Device getDevice(ChannelHandlerContext ctx) {
        String ip = getIp(ctx);
        if(DLampManager.getDeviceMap().containsKey(ip)) {
            return DLampManager.getDeviceMap().get(ip);
        }
        return null;
    }
}
