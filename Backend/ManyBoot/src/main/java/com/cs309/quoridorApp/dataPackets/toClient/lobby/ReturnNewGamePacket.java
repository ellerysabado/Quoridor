package com.cs309.quoridorApp.dataPackets.toClient.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.game.Game;

/**
 * Packet used to return a game's ID to the client
 */
public class ReturnNewGamePacket extends ClientPacket
{
    private String session = "AAAAAA";

    public ReturnNewGamePacket(Game game)
    {
        this.session = game.id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
