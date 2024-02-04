package com.cs309.quoridorApp.packets;

import java.util.UUID;

public class TestMovementPacket implements IPacket
{
    private String id;
    private String gameId;
    private int x;
    private int y;

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public String getGameId()
    {
        return gameId;
    }

    public String getId()
    {
        return id;
    }
}
