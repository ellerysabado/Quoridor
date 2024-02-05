package com.cs309.tutorial.quoridorApp.packets;

import java.util.UUID;

public class TestMovementPacket implements IPacket
{
    private UUID id;
    private int gameId;
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

    public int getGameId()
    {
        return gameId;
    }

    public UUID getId()
    {
        return id;
    }
}
