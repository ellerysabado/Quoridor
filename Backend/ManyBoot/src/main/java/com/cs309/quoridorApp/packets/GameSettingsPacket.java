package com.cs309.quoridorApp.packets;

public class GameSettingsPacket implements IPacket
{
    private int bots;

    private String id;
    private String gameId;

    public String getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public int getBots() {
        return bots;
    }

    public void setBots(int bots) {
        this.bots = bots;
    }
}
