package com.mclans.mod.dlamp.listener;

import com.mclans.mod.dlamp.event.AirkissSuccessedEvent;
import com.mclans.mod.dlamp.event.DeviceConnectedEvent;
import com.mclans.mod.dlamp.event.DeviceDiscoveredEvent;
import com.mclans.mod.dlamp.event.PowerSwitchedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeviceListener {
    @SubscribeEvent
    public void onDeviceDiscovered(DeviceDiscoveredEvent event) {

    }
    @SubscribeEvent
    public void onAirkissSuccessed(AirkissSuccessedEvent event) {

    }
    @SubscribeEvent
    public void onDeviceConnected(DeviceConnectedEvent event) {

    }
    @SubscribeEvent
    public void onPowerSwitched(PowerSwitchedEvent event) {

    }
}
