package com.mclans.mod.dlamp;

import com.mclans.mod.dlamp.runnable.AirKissBroadcast;
import com.mclans.mod.dlamp.runnable.AirKissServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DLampManager {

    private static ExecutorService airkissService = Executors.newFixedThreadPool(3);

    public static void startAirkiss(String ssid, String password) {
        AirKissBroadcast broadcast = new AirKissBroadcast(ssid, password);
        AirKissServer server = new AirKissServer(broadcast.getRandomChar());
        airkissService.submit(broadcast);
        airkissService.submit(server);
    }

    public void stopAirkiss() {
        airkissService.shutdown();
    }

}
