package com.mclans.mod.dlamp.runnable;

import com.mclans.mod.dlamp.data.Device;
import com.mclans.mod.dlamp.handler.DeviceHandler;
import com.mclans.mod.dlamp.protocol.ProtocolDecoder;
import com.mclans.mod.dlamp.protocol.ProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class DeviceConnector {
    private EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap b;

    public DeviceConnector(Device device) {
        b = new Bootstrap();
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        b.option(ChannelOption.TCP_NODELAY, true);
        b.channel(NioSocketChannel.class);
        b.group(group);
        b.remoteAddress(device.getIp(), 1516);
        b.handler(
            new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel channel) {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
                    pipeline.addLast(new ProtocolDecoder());
                    pipeline.addLast(new ProtocolEncoder());
                    pipeline.addLast(new DeviceHandler());
                }
            }
        );
    }

    public void connect() {
        if(channel != null && channel.isActive()) return;
        try {
            ChannelFuture channelFuture = b.connect().sync();
            channelFuture.addListener((ChannelFutureListener) futureListener -> {
                if(futureListener.isSuccess()) {
                    channel = channelFuture.channel();
                    System.out.println("链接成功");
                } else {
                    System.out.println("链接失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return this.channel;
    }
}
