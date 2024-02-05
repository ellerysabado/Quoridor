package com.cs309.tutorial.quoridorApp.packets;

import java.util.UUID;

public class GamePacket implements IPacket
{
    private UUID id;
    private int gameId;

    public UUID getId() {
        return id;
    }

    public int getGameId() {
        return gameId;
    }
}
