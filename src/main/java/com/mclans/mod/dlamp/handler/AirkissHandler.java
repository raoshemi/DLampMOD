package com.mclans.mod.dlamp.handler;

import com.mclans.mod.dlamp.event.AirkissSuccessedEvent;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class AirkissHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private char randomChar;
    private List<String> deviceList = new ArrayList<>();
    public AirkissHandler(char randomChar) {
        this.randomChar = randomChar;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) {
        if(packet.content().readableBytes() == 7) {
            byte b = packet.content().getByte(0);
            if ((byte) randomChar == b) {
                byte[] macBytes = new byte[6];
                packet.content().getBytes(1, macBytes);
                String mac = ByteBufUtil.hexDump(macBytes);
                if(!deviceList.contains(mac)) {
                    deviceList.add(mac);
                    // TODO: 触发AirKiss成功事件
                    MinecraftForge.EVENT_BUS.post(new AirkissSuccessedEvent(mac));
                    System.out.println("Device: " + mac + " connected");
                }
            }
        }
    }
}
