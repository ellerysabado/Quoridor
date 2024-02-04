package com.cs309.quoridorApp.dataPackets.toClient.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.game.Game;
import com.cs309.quoridorApp.game.GameRules;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;

//TODO: ADD TO AS MORE SETTINGS ARE ADDED

/**
 * Returns game's settings: max player count, which player is the host,
 * the amount of bots in game, and number of walls each player starts
 * with.
 *
 * Accepts entire game as parameter.
 */
public class ReturnSettingsPacket extends ClientPacket
{

    private int player_count;
    private int host;
    private int botCount;
    private int numWalls;

    public ReturnSettingsPacket(Game game)
    {
        GameRules r = game.getRules();
        PlayerList pl = game.getClientList();
        player_count = r.getMaxPlayers();
        botCount = r.getBots();
        host = 0;

        numWalls = r.getMaxWalls();

        for(int i = 0; i < pl.getClients().size(); i++)
            if(pl.isHost(pl.getClients().get(i)))
                host = i;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public int getHost() {
        return host;
    }

    public int getBotCount() {
        return botCount;
    }

    public int getPlayer_count() {
        return player_count;
    }

    public void setBotCount(int botCount) {
        this.botCount = botCount;
    }

    public void setPlayer_count(int player_count) {
        this.player_count = player_count;
    }

    public int getNumWalls() {
        return numWalls;
    }

    public void setNumWalls(int numWalls) {
        this.numWalls = numWalls;
    }
}
