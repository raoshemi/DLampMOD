package com.mclans.mod.dlamp.event;

import net.minecraftforge.fml.common.eventhandler.Event;

// 配网成功事件
public class AirkissSuccessedEvent extends Event {
    private String mac;
    public AirkissSuccessedEvent(String mac) {
        this.mac = mac;
    }
    public String getMac() {
        return mac;
    }
}
