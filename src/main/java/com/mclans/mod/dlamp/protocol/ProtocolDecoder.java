package com.mclans.mod.dlamp.protocol;

import com.mclans.mod.dlamp.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by dr.jin on 2017/10/21.
 */
public class ProtocolDecoder extends MessageToMessageDecoder {
    ByteBuf temp = Unpooled.buffer();
    int length = 0;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        if (o instanceof DatagramPacket) { //如果返回的是数据报
            DatagramPacket datagramPacket = (DatagramPacket) o;
            ByteBuf buf = datagramPacket.content();
//            Utils.log("接受数据报数据", buf);
            if (Packet.readHead(buf)) {
                int len = Packet.readVarInt(buf);
                ByteBuf packetdata = buf.readBytes(len);
                Packet packet = Protocol.createPacket(packetdata);
                if (packet != null) {
//                    packet.setSenderAddress(datagramPacket.sender().getAddress());
//                    packet.setRecipientAddress(datagramPacket.recipient().getAddress());
                    list.add(packet);
                }
//                Utils.log("received packetname", packet.getClass().getName());
            }
        }
        if (o instanceof ByteBuf) { //如果返回的是ByteBuf
//            ByteBuf byteBuf = (ByteBuf) o;
//            Utils.log("Data:",byteBuf);
//            Utils.log("接受设备通讯数据", byteBuf);
//            if (Packet.readHead(byteBuf)){
//                int len = Packet.readVarInt(byteBuf);
//                ByteBuf packet_data = byteBuf.readBytes(len);
//                Packet packet = Protocol.createPacket(packet_data);
//                list.add(packet);
////                Utils.log("获得的包名为: ", packet.getClass().getName());
//            }
            temp.writeBytes((ByteBuf) o);
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
//                System.out.println(temp.readableBytes());
//                System.out.println(length);
                if (length != 0 && (temp.readableBytes() >= length)) {
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
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

//        protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
//        ByteBuf buf = datagramPacket.content();
//        Utils.log("received data", buf);
//        if(Packet.readHead(buf)) {
//            int len = Packet.readVarInt(buf);
//            ByteBuf packetdata = buf.readBytes(len);
//            Packet packet = Protocol.createPacket(packetdata);
//            packet.setSenderAddress(datagramPacket.sender().getAddress());
//            packet.setRecipientAddress(datagramPacket.recipient().getAddress());
//            list.add(packet);
//            Utils.log("received packetname", packet.getClass().getName());
//        }
//
//    }
}