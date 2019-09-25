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
//        DEVICE_FOUND_FLAG(new byte[]{0x00, 0x00, 0x04}, DeviceFoundPacket.class),
//        HANDSHAKE_REQUEST_FLAG(new byte[]{0x00, 0x00, 0x06}, HandshakeRequestPacket.class),
//        HANDSHAKE_RESPONSE_FLAG(new byte[]{0x00, 0x00, 0x07}, HandshakeResponsePacket.class),
//        LOGIN_REQUEST_FLAG(new byte[]{0x00, 0x00, 0x08}, LoginRequestPacket.class),
//        LOGIN_RESPONSE_FLAG(new byte[]{0x00, 0x00, 0x09}, LoginResponsePacket.class),
//        HEARTBEAT_REQUEST_FLAG(new byte[]{0x00, 0x00, 0x15}, HeartBeatRequestPacket.class),
//        HEARTBEAT_RESPONSE_FLAG(new byte[]{0x00, 0x00, 0x16}, HeartBeatResponsePacket.class),
//        STATUS_REQUEST_FLAG(new byte[]{0x00, 0x00, (byte) 0x93}, StatusRequestPacket.class),
//        STATUS_RESPONSE_FLAG(new byte[]{0x00, 0x00, (byte) 0x94}, StatusResponsePacket.class),
//        CONTROL_RESPONSE_FLAG(new byte[]{0x01, 0x00, (byte) 0x93}, ReportPacket.class),
//        CONTROL_REQUEST_FLAG(new byte[]{0x00, 0x00, (byte) 0x93}, ControlRequestPacket.class),
//        SUBSCRIBE_REQUEST_FLAG(new byte[]{0x00, 0x00, (byte) 0x90}, SubscribeRequestPacket.class),
//        SUBSCRIBE_RESPONSE_FLAG(new byte[]{0x00, 0x00, (byte) 0x91}, SubscribeResponsePacket.class),
//        BOOTSTRAP_FLAG(new byte[]{0x00, 0x00, 0X05}, DeviceDiscoverBroadcastPacket.class);

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

//    public static DataPoint readDataPoint(ByteBuf buf) {
//        DataPoint dataPoint = new DataPoint();
//        byte switch_point = buf.readByte();
//        String switch_point_binary = byte2BinaryString(switch_point);
//        String[] strings = switch_point_binary.split("");

//        dataPoint.setLamp_on(strings[7].equalsIgnoreCase("1"));
//        dataPoint.setLamp_on_random(strings[6].equalsIgnoreCase("1"));
//        dataPoint.setLamp_off_random(strings[5].equalsIgnoreCase("1"));
//        dataPoint.setCtl_on_random(strings[4].equalsIgnoreCase("1"));
//        dataPoint.setCtl_off_random(strings[3].equalsIgnoreCase("1"));
//        dataPoint.setEffect_on_random(strings[2].equalsIgnoreCase("1"));
//        dataPoint.setEffect_off_random(strings[1].equalsIgnoreCase("1"));
//        dataPoint.setLamp_on_b(byte2Int(buf.readByte()));
//        dataPoint.setLamp_off_b(byte2Int(buf.readByte()));
//        dataPoint.setLamp_on2off_acceleration(byte2Int(buf.readByte()));
//        dataPoint.setLamp_off2on_acceleration(byte2Int(buf.readByte()));
//        dataPoint.setCtl_on_b(byte2Int(buf.readByte()));
//        dataPoint.setCtl_off_b(byte2Int(buf.readByte()));
//        dataPoint.setCtl_on2off_acceleration(byte2Int(buf.readByte()));
//        dataPoint.setCtl_off2on_acceleration(byte2Int(buf.readByte()));
//        dataPoint.setEffect_on_b(byte2Int(buf.readByte()));
//        dataPoint.setEffect_off_b(byte2Int(buf.readByte()));
//        dataPoint.setEffect_on2off_acceleration(byte2Int(buf.readByte()));
//        dataPoint.setEffect_off2on_acceleration(byte2Int(buf.readByte()));
//        dataPoint.setEffect_times(byte2Int(buf.readByte()));
//        dataPoint.setLamp_on_s(byte2Int(buf.readByte()));
//        dataPoint.setLamp_off_s(byte2Int(buf.readByte()));
//        dataPoint.setCtl_on_s(byte2Int(buf.readByte()));
//        dataPoint.setCtl_off_s(byte2Int(buf.readByte()));
//        dataPoint.setEffect_on_s(byte2Int(buf.readByte()));
//        dataPoint.setEffect_off_s(byte2Int(buf.readByte()));
//        dataPoint.setLamp_on_h(buf.readShort());
//        dataPoint.setLamp_off_h(buf.readShort());
//        dataPoint.setLamp_on_interval(buf.readShort());
//        dataPoint.setLamp_on2off_interval(buf.readShort());
//        dataPoint.setLamp_off_interval(buf.readShort());
//        dataPoint.setLamp_off2on_interval(buf.readShort());
//        dataPoint.setCtl_on_h(buf.readShort());
//        dataPoint.setCtl_off_h(buf.readShort());
//        dataPoint.setCtl_on_interval(buf.readShort());
//        dataPoint.setCtl_on2off_interval(buf.readShort());
//        dataPoint.setCtl_off_interval(buf.readShort());
//        dataPoint.setCtl_off2on_interval(buf.readShort());
//        dataPoint.setEffect_on_h(buf.readShort());
//        dataPoint.setEffect_off_h(buf.readShort());
//        dataPoint.setEffect_on_interval(buf.readShort());
//        dataPoint.setEffect_on2off_interval(buf.readShort());
//        dataPoint.setEffect_off_interval(buf.readShort());
//        dataPoint.setEffect_off2on_interval(buf.readShort());
//        dataPoint.getFlag();
//        return dataPoint;
//    }

    public static byte[] writeDataPoint(DataPoint dataPoint) {
        byte[] bytes = new byte[62];
        ByteBuf buf = Unpooled.buffer();
        String s = "0";
//        s += dataPoint.isEffect_off_random() ? "1" : "0";
//        s += dataPoint.isEffect_on_random() ? "1" : "0";
//        s += dataPoint.isCtl_off_random() ? "1" : "0";
//        s += dataPoint.isCtl_on_random() ? "1" : "0";
//        s += dataPoint.isLamp_off_random() ? "1" : "0";
//        s += dataPoint.isLamp_on_random() ? "1" : "0";
//        s += dataPoint.isLamp_on() ? "1" : "0";

//        buf.write
//        String flag = dataPoint.getFlag();
//        byte[] head = byte2String(flag);
//        String[] key = flag.replaceAll(",", "").split("");

//        boolean f = false;
//        //System.out.println(key.length);
//        for (int i = key.length -1; i > key.length - 6; i--) {
//            if (f = key[i].equalsIgnoreCase("1")) {
//                break;
//            }
//        }
        //System.out.println(Arrays.toString(key));
        //System.out.println(Arrays.toString(head));
//        buf.writeBytes(head);
//        buf.writeByte(f ? bit2byte(s) : 0x00);
//        buf.writeByte(key[40].equalsIgnoreCase("1") ? dataPoint.getLamp_on_b() : 0x00);
//        buf.writeByte(key[39].equalsIgnoreCase("1") ? dataPoint.getLamp_off_b() : 0x00);
//        buf.writeByte(key[38].equalsIgnoreCase("1") ? dataPoint.getLamp_on2off_acceleration() : 0x00);
//        buf.writeByte(key[37].equalsIgnoreCase("1") ? dataPoint.getLamp_off2on_acceleration() : 0x00);
//        buf.writeByte(key[36].equalsIgnoreCase("1") ? dataPoint.getCtl_on_b() : 0x00);
//        buf.writeByte(key[35].equalsIgnoreCase("1") ? dataPoint.getCtl_off_b() : 0x00);
//        buf.writeByte(key[34].equalsIgnoreCase("1") ? dataPoint.getCtl_on2off_acceleration() : 0x00);
//        buf.writeByte(key[33].equalsIgnoreCase("1") ? dataPoint.getCtl_off2on_acceleration() : 0x00);
//        buf.writeByte(key[32].equalsIgnoreCase("1") ? dataPoint.getEffect_on_b() : 0x00);
//        buf.writeByte(key[31].equalsIgnoreCase("1") ? dataPoint.getEffect_off_b() : 0x00);
//        buf.writeByte(key[30].equalsIgnoreCase("1") ? dataPoint.getEffect_on2off_acceleration() : 0x00);
//        buf.writeByte(key[29].equalsIgnoreCase("1") ? dataPoint.getEffect_off2on_acceleration() : 0x00);
//        buf.writeByte(key[28].equalsIgnoreCase("1") ? dataPoint.getEffect_times() : 0x00);
//        buf.writeByte(key[27].equalsIgnoreCase("1") ? dataPoint.getLamp_on_s() : 0x00);
//        buf.writeByte(key[26].equalsIgnoreCase("1") ? dataPoint.getLamp_off_s() : 0x00);
//        buf.writeByte(key[25].equalsIgnoreCase("1") ? dataPoint.getCtl_on_s() : 0x00);
//        buf.writeByte(key[24].equalsIgnoreCase("1") ? dataPoint.getCtl_off_s() : 0x00);
//        buf.writeByte(key[23].equalsIgnoreCase("1") ? dataPoint.getEffect_on_s() : 0x00);
//        buf.writeByte(key[22].equalsIgnoreCase("1") ? dataPoint.getEffect_off_s() : 0x00);
//        buf.writeShort(key[21].equalsIgnoreCase("1") ? dataPoint.getLamp_on_h() : 0x00);
//        buf.writeShort(key[20].equalsIgnoreCase("1") ? dataPoint.getLamp_off_h() : 0x00);
//        buf.writeShort(key[19].equalsIgnoreCase("1") ? dataPoint.getLamp_on_interval() : 0x00);
//        buf.writeShort(key[18].equalsIgnoreCase("1") ? dataPoint.getLamp_on2off_interval() : 0x00);
//        buf.writeShort(key[17].equalsIgnoreCase("1") ? dataPoint.getLamp_off_interval() : 0x00);
//        buf.writeShort(key[16].equalsIgnoreCase("1") ? dataPoint.getLamp_off2on_interval() : 0x00);
//        buf.writeShort(key[15].equalsIgnoreCase("1") ? dataPoint.getCtl_on_h() : 0x00);
//        buf.writeShort(key[14].equalsIgnoreCase("1") ? dataPoint.getCtl_off_h() : 0x00);
//        buf.writeShort(key[13].equalsIgnoreCase("1") ? dataPoint.getCtl_on_interval() : 0x00);
//        buf.writeShort(key[12].equalsIgnoreCase("1") ? dataPoint.getCtl_on2off_interval() : 0x00);
//        buf.writeShort(key[11].equalsIgnoreCase("1") ? dataPoint.getCtl_off_interval() : 0x00);
//        buf.writeShort(key[10].equalsIgnoreCase("1") ? dataPoint.getCtl_off2on_interval() : 0x00);
//        buf.writeShort(key[9].equalsIgnoreCase("1") ? dataPoint.getEffect_on_h() : 0x00);
//        buf.writeShort(key[8].equalsIgnoreCase("1") ? dataPoint.getEffect_off_h() : 0x00);
//        buf.writeShort(key[7].equalsIgnoreCase("1") ? dataPoint.getEffect_on_interval() : 0x00);
//        buf.writeShort(key[6].equalsIgnoreCase("1") ? dataPoint.getEffect_on2off_interval() : 0x00);
//        buf.writeShort(key[5].equalsIgnoreCase("1") ? dataPoint.getEffect_off_interval() : 0x00);
//        buf.writeShort(key[4].equalsIgnoreCase("1") ? dataPoint.getEffect_off2on_interval() : 0x00);

        buf.readBytes(bytes);

        return bytes;
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
