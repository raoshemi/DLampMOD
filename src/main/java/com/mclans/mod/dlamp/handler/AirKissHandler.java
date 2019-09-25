package com.mclans.mod.dlamp.handler;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.ArrayList;
import java.util.List;

public class AirKissHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private char randomChar;
    private List<String> deviceList = new ArrayList<>();
    public AirKissHandler(char randomChar) {
        this.randomChar = randomChar;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        if(packet.content().readableBytes() == 7) {
            byte b = packet.content().getByte(0);
            if ((byte) randomChar == b) {
                byte[] macBytes = new byte[6];
                packet.content().getBytes(1, macBytes);
                String mac = ByteBufUtil.hexDump(macBytes);
                if(!deviceList.contains(mac)) {
                    deviceList.add(mac);
                    // TODO: 触发AirKiss成功事件
                    System.out.println("Device: " + mac + " connected");
                }
            }
        }
    }
}
