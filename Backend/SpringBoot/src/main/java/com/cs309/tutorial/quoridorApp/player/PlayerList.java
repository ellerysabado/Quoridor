package com.cs309.tutorial.quoridorApp.player;

import com.cs309.tutorial.quoridorApp.game.Game;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.PlayerMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class PlayerList
{
    /*
    holds players and specifies who is the host, the players, and spectators
    also makes it possible to switch out or mute players
     */

    private HashMap<Player, PlayerListData> clientList = new HashMap<>();

    public final Game game;

    public PlayerList(Game game)
    {
        this.game = game;
    }


    public void setHost(Player player) {
        Player curHost = getHost();
        if(curHost != null && curHost != player)
            clientList.get(curHost).host = false;
        clientList.get(player).host = true;
    }

    public Player getHost() {
        Player toReturn = null;
        for(PlayerListData data : clientList.values())
        {
            if(toReturn != null && data.host)
                data.host = false;
            else if(data.host)
                toReturn = data.player;
        }
        return toReturn;
    }

    public boolean isHost(Player player) {
        return isClient(player) && clientList.get(player).host;
    }

    public void addClient(Player player) {
        clientList.put(player, new PlayerListData(player));
    }

    public void setPlay(Player player, boolean isPlaying) {
        PlayerListData pData = clientList.get(player);
        if(!isPlaying && pData.isPlaying()) {
            pData.marker.remove();
            pData.marker = null;
        }
        else if (isPlaying && !pData.isPlaying()) {
            pData.marker = game.getRules().initPlayer(player); //TODO: set have input be playerlistdata
        }
    }

    public boolean isPlaying(Player player) {
        return clientList.get(player).isPlaying();
    }

    public List<Player> getPlayers() {
        return sortClients(player-> {
            return clientList.get(player).isPlaying();
        });
    }

    public boolean isSpectating(Player player)
    {
        return !clientList.get(player).isPlaying();
    }

    public List<Player> getSpectators() {
        return sortClients(player-> {
            return !clientList.get(player).isPlaying();
        });
    }

    public boolean isClient(Player player)
    {
        return clientList.get(player) != null;
    }

    public List<Player> getClients() {
        return (List<Player>) clientList.keySet();
    }

    public List<Player> sortClients(Predicate<Player> pred) {
        List<Player> toReturn = new ArrayList<>();
        for(Player player : clientList.keySet())
            if(pred.test(player))
                toReturn.add(player);
        return toReturn;
    }

    public void mute(Player player, boolean mute) {
        clientList.get(player).mute = mute;
    }

    public boolean isMute(Player player) {
        return clientList.get(player).mute;
    }

    public PlayerMarker getPlayerMaker(Player player)
    {
        if(!isClient(player))
            return null;
        return clientList.get(player).marker;
    }

    public void setTeam(Player player, int team)
    {
        if(clientList.get(player) != null)
            clientList.get(player).team = team%7;
    }

    public int getTeam(Player player)
    {
        if(clientList.get(player) != null)
            return clientList.get(player).team;
        return 0;
    }

    private class PlayerListData
    {
        boolean host = false;
        PlayerMarker marker = null;
        boolean mute = false;
        int team = 0;
        Player player;

        public PlayerListData(Player player) {
            this.player = player;
        }

        public boolean isPlaying()
        {
            return marker != null;
        }
    }
}
