package com.mclans.mod.dlamp.protocol;

import com.mclans.mod.dlamp.data.DataPoint;
import com.mclans.mod.dlamp.protocol.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Protocol {
    public static final String PRODUCTKEY = "28cb1ade4c0843eb9449e2962dbc3477";
    private static final String packagename = "com.mclans.dlamplib.protocol.packet.";
    public static int PACKET_INDEX = 1;
    public static final byte[] HEAD = new byte[]{0x00, 0x00, 0x00, 0x05};

    public enum Flag {
        DISCOVER_BROADCAST(new byte[]{0x00, 0x00, 0x05}, DeviceDiscoverBroadcastPacket.class),
        DISCOVER_RESPONSE(new byte[]{0x00, 0x00, 0x06}, DeviceDiscoverResponsePacket.class),
        HANDSHAKE(new byte[]{0x00, 0x00, 0x07}, HandshakePacket.class),
        HANDSHAKE_SUCCESS(new byte[]{0x00, 0x00, 0x08}, HandshakeSuccessPacket.class),
        LOGIN(new byte[]{0x00, 0x00, 0x09}, LoginPacket.class),
        LOGIN_SUCCESS(new byte[]{0x00, 0x00, 0x0a}, LoginSuccessPacket.class),
        DATAPOINT_CHANGE(new byte[]{0x00, 0x00, 0x20}, DataPointChangePacket.class),
        PING(new byte[]{0x00, 0x00, 0x30}, PingPacket.class),
        PONG(new byte[]{0x00, 0x00, 0x31}, PongPacket.class),
        POWER_SWITCH(new byte[]{0x00, 0x00, 0x40}, PowerSwitchPacket.class);
        private byte[] flag;
        private Class packetname;

        Flag(byte[] flag, Class packetname) {
            this.flag = flag;
            this.packetname = packetname;
        }

        public static Flag getByFlag(byte[] flag) {
            for (Flag f : values()) {
                if (Arrays.equals(f.getFlag(), flag)) {
                    return f;
                }
            }
            return null;
        }

        public byte[] getFlag() {
            return this.flag;
        }

        public void setFlag(byte[] flag) {
            this.flag = flag;
        }

        public Class getPacketName() {
            return this.packetname;
        }

        public void setPacketname(Class packetname) {
            this.packetname = packetname;
        }
    }


    public static Packet createPacket(ByteBuf buf) throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        byte[] flag = Packet.readFlag(buf);
        String s = Flag.getByFlag(flag).getPacketName().toString().replaceAll("class ", "");
        Packet packet = (Packet) Class.forName(s).newInstance();
        packet.read(buf);
        return packet;
    }

    private static String byte2BinaryString(byte b) {
        return Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
    }

    public static byte bit2byte(String bString) {
        byte result = 0;
        for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
            result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
        }
        return result;
    }

    public static byte[] byte2String(String binaryByteString) {
        //假设binaryByte 是01，10，011，00以，分隔的格式的字符串
        String[] binaryStr = binaryByteString.split(",");
        byte[] byteArray = new byte[binaryStr.length];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) parse(binaryStr[i]);
        }
        return byteArray;
    }

    private static int parse(String str) {
        //32位 为负数
        if (32 == str.length()) {
            str = "-" + str.substring(1);
            return -(Integer.parseInt(str, 2) + Integer.MAX_VALUE + 1);
        }
        return Integer.parseInt(str, 2);
    }
}
