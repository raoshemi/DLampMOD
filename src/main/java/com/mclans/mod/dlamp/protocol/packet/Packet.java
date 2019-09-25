package com.mclans.mod.dlamp.protocol.packet;

import com.mclans.mod.dlamp.protocol.OverflowPacketException;
import com.mclans.mod.dlamp.protocol.Protocol;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public abstract class Packet {
    protected String packetName;
//    public InetAddress getSenderAddress() {
//        return this.senderAddress;
//    }
//
//    public void setSenderAddress(InetAddress senderAddress) {
//        this.senderAddress = senderAddress;
//    }
//
//    public InetAddress getRecipientAddress() {
//        return this.recipientAddress;
//    }
//
//    public void setRecipientAddress(InetAddress recipientAddress) {
//        this.recipientAddress = recipientAddress;
//    }

    public static void writeString(String s, ByteBuf buf) {
        if (s.length() > 32767) {
            throw new OverflowPacketException(String.format("Cannot send string longer than Short.MAX_VALUE (got %s characters)", new Object[]{Integer.valueOf(s.length())}));
        }
        byte[] b = new byte[0];
        try {
            b = s.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        writeVarInt(b.length, buf);
        buf.writeBytes(b);
    }

    protected static String readString(ByteBuf buf, int len) throws UnsupportedEncodingException {
        if (len > 32767) {
            throw new OverflowPacketException(String.format("Cannot receive string longer than Short.MAX_VALUE (got %s characters)", new Object[]{Integer.valueOf(len)}));
        }
        byte[] b = new byte[len];
        buf.readBytes(b);

        return new String(b, "utf-8");
    }


    public static boolean readHead(ByteBuf input) {
        byte[] head = new byte[4];
        input.readBytes(head);
        return Arrays.equals(Protocol.HEAD, head);
    }


    public static byte[] readFlag(ByteBuf input) {
        byte[] flag = new byte[3];
        input.readBytes(flag);
        return flag;
    }


    public static int readVarInt(ByteBuf input) {
        return readVarInt(input, 5);
    }


    private static int readVarInt(ByteBuf input, int maxBytes) {
        int out = 0;
        int bytes = 0;
        for (; ; ) {
            byte in = input.readByte();
            out |= (in & 0x7F) << bytes++ * 7;
            if (bytes > maxBytes) {
                throw new RuntimeException("VarInt too big");
            }
            if ((in & 0x80) != 128) {
                break;
            }
        }
        return out;
    }


    public static void writeVarInt(int value, ByteBuf output) {
        for (; ; ) {
            int part = value & 0x7F;

            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }
            output.writeByte(part);
            if (value == 0) {
                break;
            }
        }
    }


    public static int readVarShort(ByteBuf buf) {
        int low = buf.readUnsignedShort();
        int high = 0;
        if ((low & 0x8000) != 0) {
            low &= 0x7FFF;
            high = buf.readUnsignedByte();
        }
        return (high & 0xFF) << 15 | low;
    }


    public static void writeVarShort(ByteBuf buf, int toWrite) {
        int low = toWrite & 0x7FFF;
        int high = (toWrite & 0x7F8000) >> 15;
        if (high != 0) {
            low |= 0x8000;
        }
        buf.writeShort(low);
        if (high != 0) {
            buf.writeByte(high);
        }
    }

    public void read(ByteBuf buf) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Packet must implement read method");
    }


    public void write(ByteBuf buf) {
        throw new UnsupportedOperationException("Packet must implement write method");
    }

    public static byte[] mergeBytes(byte[]... b) {
        int i = 0;
        for (byte[] bb : b) {
            i += bb.length;
        }

        byte[] back = new byte[i];
        int i1 = 0;
        for (byte[] bytes : b) {
            for (byte by : bytes) {
                back[i1] = by;
                i1++;
            }
        }
        return back;
    }

    protected static String bytes2HexString(ByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes2HexString(bytes);
    }


    protected static String bytes2HexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte aBArray : bArray) {
            sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }



    protected static int bytesToInt2(byte[] src) {
        int value;
        byte[] bytes = new byte[4];
        if (src.length < 4) {
            System.arraycopy(src, 0, bytes, bytes.length - src.length, src.length);
        }
        if (src.length > 4) {
            System.arraycopy(src, 0, bytes, 0, bytes.length);
        }
        value = (int) (((bytes[0] & 0xFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | (bytes[3] & 0xFF));
        return value;
    }

    public static int byte2Int(byte b) {
        int value;
        byte[] bytes = new byte[]{0x00, 0x00, 0x00, b};
        value = (int) (((bytes[0] & 0xFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | (bytes[3] & 0xFF));
        return value;
    }

    public Packet() {
        this.packetName = "Packet";
    }
    public void setPacketName(String packetName) {
        this.packetName = packetName;
    }
    public String getPacketName() {
        return this.packetName;
    }
}
