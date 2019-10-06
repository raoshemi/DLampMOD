package com.mclans.mod.dlamp.event;

import com.mclans.mod.dlamp.data.Device;
import net.minecraftforge.fml.common.eventhandler.Event;

// 设备电源状态已切换事件
public class PowerSwitchedEvent extends Event {
    private Device device;
    public PowerSwitchedEvent(Device device) {
        this.device = device;
    }
    public Device getDevice() {
        return device;
    }
}
