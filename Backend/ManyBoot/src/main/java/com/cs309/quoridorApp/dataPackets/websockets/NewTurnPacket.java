package com.cs309.quoridorApp.dataPackets.websockets;

import com.cs309.quoridorApp.dataPackets.ListedPlayer;

public class NewTurnPacket extends WebsocketPacket
{
    private ListedPlayer player;

    public NewTurnPacket(ListedPlayer player)
    {
        setFunction("NEWTURN");
        this.player = player;
    }

    public ListedPlayer getPlayer() {
        return player;
    }

    public void setPlayer(ListedPlayer player) {
        this.player = player;
    }
}