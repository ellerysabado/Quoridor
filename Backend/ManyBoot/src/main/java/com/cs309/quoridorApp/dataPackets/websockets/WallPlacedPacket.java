package com.cs309.quoridorApp.dataPackets.websockets;

import com.cs309.quoridorApp.dataPackets.ListedPlayer;

public class WallPlacedPacket extends WebsocketPacket
{
    private ListedPlayer player;
    private int x;
    private int y;
    private int direction;

    public WallPlacedPacket(ListedPlayer player, int x, int y, boolean isHorizontal)
    {
        setFunction("WALLPLACED");
        this.player = player;
        this.x = x;
        this.y = y;
        this.direction = isHorizontal ? 0 : 1;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}