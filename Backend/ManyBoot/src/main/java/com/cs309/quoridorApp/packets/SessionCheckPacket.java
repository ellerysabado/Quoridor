package com.cs309.quoridorApp.packets;

import java.util.UUID;

public class SessionCheckPacket implements IPacket
{
    private String id;

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
