package com.cs309.quoridorApp.dataPackets.toClient.lobby;

import com.cs309.quoridorApp.dataPackets.ListedPlayer;
import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toServer.lobby.GetPlayersPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Packet that holds a list of players
 */
public class ReturnPlayersPacket extends ClientPacket
{

    private List<ListedPlayer> players = new ArrayList<>();

    public ReturnPlayersPacket(List players)
    {
        setResult(true);

        this.players = players;
    }

    public void setPlayers(List players) {
        this.players = players;
    }

    public List getPlayers() {
        return players;
    }

}
