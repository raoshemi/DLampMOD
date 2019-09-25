package com.mclans.mod.dlamp.protocol;

import com.mclans.mod.dlamp.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by dr.jin on 2017/10/21.
 */
public class ProtocolEncoder extends MessageToMessageEncoder<Packet> {
    private String ip = "";
    private int port = 0;
    public ProtocolEncoder(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ProtocolEncoder() {

    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        ByteBuf out = Unpooled.buffer();
        packet.write(buf);
        int len = buf.readableBytes();
        out.writeBytes(Protocol.HEAD);
        Packet.writeVarInt(len, out);
        out.writeBytes(buf);
//        packet.write(out);
//        if(ip.length() > 0) {
//            list.add(new DatagramPacket(out, new InetSocketAddress(ip, port)));
//        } else {
            list.add(out);
//        }/


//        Utils.log("send bytes", out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}