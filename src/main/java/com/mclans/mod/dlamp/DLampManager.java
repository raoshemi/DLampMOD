package com.mclans.mod.dlamp;

import com.mclans.mod.dlamp.data.Device;
import com.mclans.mod.dlamp.protocol.packet.plugin.RemoteRequestPacket;
import com.mclans.mod.dlamp.runnable.AirKissBroadcast;
import com.mclans.mod.dlamp.runnable.AirKissServer;
import com.mclans.mod.dlamp.runnable.DeviceDiscoverBroadcast;
import com.mclans.mod.dlamp.runnable.DeviceDiscoverServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DLampManager {
    private static ConcurrentHashMap<String, Device> deviceMap = new ConcurrentHashMap<>(); // 主键为IP
    private static ExecutorService airkissService = Executors.newFixedThreadPool(3);
    private static ExecutorService discoverService = Executors.newFixedThreadPool(3);

    public static void startAirkiss(String ssid, String password) {
        AirKissBroadcast broadcast = new AirKissBroadcast(ssid, password);
        AirKissServer server = new AirKissServer(broadcast.getRandomChar());
        airkissService.submit(broadcast);
        airkissService.submit(server);
    }

    public void stopAirkiss() {
        airkissService.shutdown();
    }

    public static void startDiscover() {
        DeviceDiscoverBroadcast broadcast = new DeviceDiscoverBroadcast();
        DeviceDiscoverServer server = new DeviceDiscoverServer();
        discoverService.submit(broadcast);
        discoverService.submit(server);
    }
    public static void stopDiscover() {
        discoverService.shutdown();
    }
    public static void remoteRequest(Device device) {
        ByteBuf buf = Unpooled.buffer();
        RemoteRequestPacket packet = new RemoteRequestPacket(device);
        packet.write(buf);
        FMLProxyPacket fmlProxyPacket = new FMLProxyPacket(new PacketBuffer(buf), "DLamp");
        DLamp.networkEvent.sendToServer(fmlProxyPacket);
    }
    public static ConcurrentHashMap<String, Device> getDeviceMap() {
        return deviceMap;
    }
}
