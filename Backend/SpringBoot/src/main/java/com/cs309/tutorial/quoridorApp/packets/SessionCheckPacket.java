package com.cs309.tutorial.quoridorApp.packets;

import java.util.UUID;

public class SessionCheckPacket implements IPacket
{
    private UUID id;

    public UUID getId()
    {
        return id;
    }
}
