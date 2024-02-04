package com.cs309.quoridorApp.dataPackets.toClient;

import com.cs309.quoridorApp.dataPackets.Packet;

/**
 * Packet returned to client.
 *
 * Most just stores data that will be sent to client.
 *
 * A few accept more complex parameters to convert into data to
 * store.
 */
public abstract class ClientPacket extends Packet
{
    //TODO:REMOVE RESULT
    private boolean result = false;
    private String message = "";

//    public ClientPacket(String rank) {
//        super();
//    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
