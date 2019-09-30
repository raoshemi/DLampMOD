package com.mclans.mod.dlamp.runnable;

import com.mclans.mod.dlamp.handler.DeviceDiscoverHandler;
import com.mclans.mod.dlamp.protocol.ProtocolDecoder;
import com.mclans.mod.dlamp.protocol.ProtocolEncoder;
import com.mclans.mod.dlamp.protocol.packet.DeviceDiscoverBroadcastPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class DeviceDiscoverBroadcast implements Runnable {
    private Channel ch;
    private Bootstrap b;
    private EventLoopGroup group;
    private DeviceDiscoverBroadcastPacket packet = new DeviceDiscoverBroadcastPacket();
    public DeviceDiscoverBroadcast() {
        group = new NioEventLoopGroup();
        try {
            b = new Bootstrap();
            b.group(group);
            b.channel(NioDatagramChannel.class);
            b.option(ChannelOption.SO_BROADCAST, true);
            b.handler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel channel) {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new ProtocolDecoder());
                    pipeline.addLast(new ProtocolEncoder("255.255.255.255", 11416));
                    pipeline.addLast(new DeviceDiscoverHandler());
                }
            });
            ch = b.bind(0).sync().channel();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {

            for (int i = 0; i < 30; i++) {
                ch.writeAndFlush(packet).sync();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
