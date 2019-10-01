package com.mclans.mod.dlamp;

import com.mclans.mod.dlamp.data.Device;
import com.mclans.mod.dlamp.runnable.AirKissBroadcast;
import com.mclans.mod.dlamp.runnable.AirKissServer;
import com.mclans.mod.dlamp.runnable.DeviceDiscoverBroadcast;
import com.mclans.mod.dlamp.runnable.DeviceDiscoverServer;

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
    public static ConcurrentHashMap<String, Device> getDeviceMap() {
        return deviceMap;
    }
}
