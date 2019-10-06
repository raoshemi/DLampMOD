package com.mclans.mod.dlamp.event;

import com.mclans.mod.dlamp.data.Device;
import net.minecraftforge.fml.common.eventhandler.Event;

// 设备被发现事件
public class DeviceDiscoveredEvent extends Event {
    private Device device;
    public DeviceDiscoveredEvent(Device device) {
        this.device = device;
    }
    public Device getDevice() {
        return device;
    }
}
