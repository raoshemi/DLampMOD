package com.mclans.mod.dlamp.runnable;

import com.mclans.mod.dlamp.protocol.AirKissEncoder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AirKissBroadcast implements Runnable {

    private AirKissEncoder airKissEncoder;

    public AirKissBroadcast(String ssid, String password) {
        this.airKissEncoder = new AirKissEncoder(ssid, password);
    }
    @Override
    public void run() {
        byte[] DUMMY_DATA = new byte[1500];
        DatagramSocket sendSocket = null;
        int[] ints = airKissEncoder.getEncodedData();
        System.out.println((byte) airKissEncoder.getRandomChar());
        try {
            sendSocket = new DatagramSocket();
            sendSocket.setBroadcast(true);
            while(true) {
                for (int i : ints) {
//                            System.out.println(ints.length + ":" + i);
                    DatagramPacket pkg = new DatagramPacket(DUMMY_DATA,
                            i,
                            InetAddress.getByName("255.255.255.255"),
                            10000);
                    sendSocket.send(pkg);
                    Thread.sleep(4);
                }
                Thread.sleep(5000);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public char getRandomChar() {
        return this.airKissEncoder.getRandomChar();
    }
}
