package com.mclans.mod.dlamp.runnable;

import com.mclans.mod.dlamp.handler.AirKissHandler;
import com.mclans.mod.dlamp.protocol.AirKissEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class AirKissServer implements Runnable {

    private char randomChar;

    public AirKissServer(char randomChar) {
        this.randomChar = randomChar;
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new AirKissHandler(randomChar));
                        }
                    });
            b.bind(10000).sync().channel().closeFuture().await();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
