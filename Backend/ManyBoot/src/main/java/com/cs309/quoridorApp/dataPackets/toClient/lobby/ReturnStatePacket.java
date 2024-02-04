package com.cs309.quoridorApp.dataPackets.toClient.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;

/**
 * Packet returns lobby state
 */
public class ReturnStatePacket extends ClientPacket
{

    private int lobby_state = 0;

    public int getLobby_state() {
        return lobby_state;
    }

    public void setLobby_state(int lobby_state) {
        this.lobby_state = lobby_state;
    }
}
