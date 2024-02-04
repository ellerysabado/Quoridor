package com.cs309.quoridorApp.dataPackets.websockets;

import com.cs309.quoridorApp.dataPackets.ListedPlayer;

public class PlayerMovedPacket extends WebsocketPacket
{
    private ListedPlayer player;
    private int x;
    private int y;

    public PlayerMovedPacket(ListedPlayer player, int x, int y)
    {
        setFunction("PLAYERMOVED");
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public ListedPlayer getPlayer() {
        return player;
    }

    public void setPlayer(ListedPlayer player) {
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
