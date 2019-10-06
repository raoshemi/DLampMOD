package com.mclans.mod.dlamp.event;

import com.mclans.mod.dlamp.data.Device;
import net.minecraftforge.fml.common.eventhandler.Event;

// 设备已连接事件
public class DeviceConnectedEvent extends Event {
    private Device device;
    public DeviceConnectedEvent(Device device) {
        this.device = device;
    }
    public Device getDevice() {
        return device;
    }
}
