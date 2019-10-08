package com.mclans.mod.dlamp.listener;

import com.mclans.mod.dlamp.DLampManager;
import com.mclans.mod.dlamp.data.RemoteInfo;
import com.mclans.mod.dlamp.protocol.Protocol;
import com.mclans.mod.dlamp.protocol.packet.Packet;
import com.mclans.mod.dlamp.protocol.packet.RemoteInfoPacket;
import com.mclans.mod.dlamp.protocol.packet.plugin.RemoteResponsePacket;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PluginListener {
    private ByteBuf temp;
    private int length;
    @SubscribeEvent
    public void onPlunginMessage(FMLNetworkEvent.ClientCustomPacketEvent event) throws Exception {
        temp.writeBytes(event.getPacket().payload());
        if (length == 0) {
            if (Packet.readHead(temp)) {
                length = Packet.readVarInt(temp);

            }
        }
        if (temp.readableBytes() >= length) {
            ByteBuf packet_data = temp.readBytes(length);
            length = 0;
            Packet packet = Protocol.createPacket(packet_data);
            if(packet == null) return;
            String packetName = packet.getPacketName();
            switch(packetName) {
                case "RemoteResponsePacket":
                    RemoteResponsePacket remoteResponsePacket = (RemoteResponsePacket) packet;
                    RemoteInfo remoteInfo = remoteResponsePacket.getRemoteInfo();
                    RemoteInfoPacket remoteInfoPacket = new RemoteInfoPacket(remoteInfo);
                    DLampManager.getDeviceMap().get(remoteResponsePacket.getDeviceIp()).getCtx().writeAndFlush(remoteInfoPacket);

                    break;

                default:
                    break;
            }
        }

    }
}
