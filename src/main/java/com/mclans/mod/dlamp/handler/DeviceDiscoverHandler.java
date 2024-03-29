package com.mclans.mod.dlamp.handler;

import com.mclans.mod.dlamp.DLampManager;
import com.mclans.mod.dlamp.data.Device;
import com.mclans.mod.dlamp.event.DeviceDiscoveredEvent;
import com.mclans.mod.dlamp.protocol.Protocol;
import com.mclans.mod.dlamp.protocol.packet.DeviceDiscoverResponsePacket;
import com.mclans.mod.dlamp.protocol.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraftforge.common.MinecraftForge;

import java.net.InetSocketAddress;

public class DeviceDiscoverHandler extends SimpleChannelInboundHandler<Packet> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        DeviceDiscoverResponsePacket packet = (DeviceDiscoverResponsePacket) msg;
        if(packet.getProductkey().equalsIgnoreCase(Protocol.PRODUCTKEY)) {
            String mac = packet.getMac();
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = inetSocketAddress.getAddress().getHostAddress();
            if(!DLampManager.getDeviceMap().containsKey(ip)) {
                Device device = new Device(mac, ip);
                MinecraftForge.EVENT_BUS.post(new DeviceDiscoveredEvent(device));
                device.connect();
                DLampManager.getDeviceMap().put(ip, device);
            }
        }
    }
}
