package com.cs309.quoridorApp.dataPackets.websockets;

import com.cs309.quoridorApp.dataPackets.Packet;

public abstract class WebsocketPacket extends Packet
{

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    private String function;


}
