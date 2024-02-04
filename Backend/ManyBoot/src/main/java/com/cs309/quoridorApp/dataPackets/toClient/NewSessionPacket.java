package com.cs309.quoridorApp.dataPackets.toClient;

/**
 * Packet used to return new user session UUID for future validation
 */
public class NewSessionPacket extends ClientPacket
{

    private String id;

    public NewSessionPacket(String id)
    {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
