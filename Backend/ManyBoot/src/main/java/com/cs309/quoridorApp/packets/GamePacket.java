package com.cs309.quoridorApp.packets;

import java.util.UUID;

public class GamePacket implements IPacket
{
    private String id;
    private String gameId;

    public GamePacket(String id, String gameId)
    {
        this.id = id;
        this.gameId = gameId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
