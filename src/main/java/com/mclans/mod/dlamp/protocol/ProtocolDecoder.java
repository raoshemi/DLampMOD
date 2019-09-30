package com.mclans.mod.dlamp.protocol;

import com.mclans.mod.dlamp.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by dr.jin on 2017/10/21.
 */
public class ProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    private ByteBuf temp = Unpooled.buffer();
    private int length = 0;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {


            temp.writeBytes(buf);
//            Utils.log("设备通讯数据：",temp);
            if (length == 0) {
                if (Packet.readHead(temp)) {
                    length = Packet.readVarInt(temp);
                    if (temp.readableBytes() >= length) {
                        ByteBuf packet_data = temp.readBytes(length);
                        length = 0;
                        Packet packet = Protocol.createPacket(packet_data);
                        if (packet != null) {
                            list.add(packet);
                        }
//                        Utils.log("获得的包名为: ", packet.getClass().getName());
                    }
                }
            } else {
                if (temp.readableBytes() >= length) {
                    ByteBuf packet_data = temp.readBytes(length);
                    length = 0;
                    Packet packet = Protocol.createPacket(packet_data);
                    if (packet != null) {
                        list.add(packet);
                    }
//                    Utils.log("获得的粘包名为: ", packet.getClass().getName());
                }
            }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}