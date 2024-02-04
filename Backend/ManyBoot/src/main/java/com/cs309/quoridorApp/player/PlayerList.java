package com.cs309.quoridorApp.player;

import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.game.board.tileBound.DoubleWall;
import com.cs309.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.quoridorApp.game.Game;
import io.swagger.models.auth.In;

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

    private HashMap<String, PlayerListData> clientList = new HashMap<>();
    private List<Player> turnOrder = new ArrayList<>();
    private int currentTurn = 0;

    public final Game game;

    public PlayerList(Game game)
    {
        this.game = game;
    }


    //makes given player host, replacing the old host if there is one
    public void setHost(Player player) {
        Player curHost = getHost();
        if(curHost != null && curHost != player)
            clientList.get(curHost.getUsername()).host = false;
        clientList.get(player.getUsername()).host = true;
    }

    //gets the current host
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

    //checks if given player is host
    public boolean isHost(Player player) {
        return isClient(player) && clientList.get(player.getUsername()).host;
    }

    //adds a new client to the game
    public void addClient(Player player) {
        clientList.put(player.getUsername(), new PlayerListData(player));
    }

    //removes client from game
    public void removeClient(Player player)
    {
        clientList.remove(player.getUsername());
    }

    //sets the client's play status
    //if theyre playing then they are in the game doing stuff
    //if they arent playing (aka spectating) then they can only see the game
    public void setPlay(Player player, boolean isPlaying) {
        PlayerListData pData = clientList.get(player.getUsername());
        if(!isPlaying && pData.isPlaying()) {
            pData.marker.remove();
            pData.marker = null;
        }
        else if (isPlaying && !pData.isPlaying()) {
            pData.marker = game.getRules().initPlayer(player); //TODO: set have input be playerlistdata
        }
    }

    //checks if given client is playing
    public boolean isPlaying(Player player) {
        return clientList.get(player.getUsername()).isPlaying();
    }

    //gets a list of all the clients who are actually playing the game
    public List<Player> getPlayers() {
        return sortClients(player-> {
            return clientList.get(player.getUsername()).isPlaying();
        });
    }

    //checks if given client is spectating
    public boolean isSpectating(Player player)
    {
        return !clientList.get(player.getUsername()).isPlaying();
    }

    //returns list of clients who are spectating the game (not actually playing, but can watch and maybe chat)
    public List<Player> getSpectators() {
        return sortClients(player-> {
            return !clientList.get(player.getUsername()).isPlaying();
        });
    }

    //checks if given client is in the game
    public boolean isClient(Player player)
    {
        return clientList.get(player.getUsername()) != null;
    }

    //returns list of clients who are in the game
    public List<Player> getClients() {
        List<Player> ps = new ArrayList<>();
        clientList.values().forEach(pData -> ps.add(pData.player));
        return ps;
    }

    //checks whether Player is a bot
    //bots are considered clients and will be included in client, player, and spectator lists
    public boolean isBot(Player player)
    {
        if(clientList.get(player.getUsername()) != null)
            return player instanceof BotPlayer;
        return false;
    }

    //returns list of bots
    public List<Player> getBots()
    {
        List<Player> bots = sortClients(p ->
        {return isBot(p);}
        );
        return bots;
    }

    //returns list of clients who arent bots
    public List<Player> getNotBots()
    {
        List<Player> nbots = sortClients(p ->
                {return !isBot(p);}
        );
        return nbots;
    }

    //checks if there are bots in the game
    public boolean hasBots()
    {
        return !getBots().isEmpty();
    }

    //returns a list of clients where each client matches the given predicate
    public List<Player> sortClients(Predicate<Player> pred) {
        List<Player> toReturn = new ArrayList<>();
        for(Player player : getClients())
            if(pred.test(player))
                toReturn.add(player);
        return toReturn;
    }

    //sets that client's chat privilages
    public void mute(Player player, boolean mute) {
        clientList.get(player.getUsername()).mute = mute;
    }

    //checks if the client is muted
    public boolean isMute(Player player) {
        return clientList.get(player.getUsername()).mute;
    }

    //get the marker on the board that corresponds to the given client
    public PlayerMarker getPlayerMaker(Player player)
    {
        if(!isClient(player))
            return null;
        return clientList.get(player.getUsername()).marker;
    }

    //sets the given client's team
    public void setTeam(Player player, int team)
    {
        if(clientList.get(player.getUsername()) != null)
            clientList.get(player.getUsername()).team = team%7;
    }

    //get the given client's team
    public int getTeam(Player player)
    {
        if(clientList.get(player.getUsername()) != null)
            return clientList.get(player.getUsername()).team;
        return 0;
    }

    //returns list of clients in given team
    public List<Player> getTeamMembers(int team) {
        return sortClients(player -> getTeam(player) == team);
    }

    public List<Integer> getTeams()
    {
        List<Integer> toReturn = new ArrayList<>();
        for(Player player : getPlayers())
            if(!toReturn.contains(getTeam(player)) && getTeam(player) > 0)
                toReturn.add(getTeam(player));

        return toReturn;
    }

    public void prepTurns()
    {
        turnOrder = getOrderedPlayers();
    }

    public Player getCurrentPlayer()
    {
        return turnOrder.size() > currentTurn ? turnOrder.get(currentTurn) : null;
    }

    public int getCurrentTurn()
    {
        return currentTurn;
    }

    public void nextTurn()
    {
        currentTurn = (currentTurn + 1)%turnOrder.size();
    }

    public List<Player> getOrderedPlayers()
    {
        List<Player> toReturn = new ArrayList<>();

        List<Player> temp = getPlayers();

        while(!temp.isEmpty())
        {
            Player leastTeam = null;
            for(int i = 0; i < temp.size(); i++)
                if(leastTeam == null || getTeam(temp.get(i)) < getTeam(leastTeam))
                    leastTeam = temp.get(i);

            toReturn.add(leastTeam);
            temp.remove(leastTeam);
        }
        return toReturn;
    }

    public void setWallsRemaining(Player player, int remaining)
    {
        clientList.get(player.getUsername()).wallsRemaining = remaining;
    }

    public int getWallsRemaining(Player player)
    {
        return clientList.get(player.getUsername()).wallsRemaining;
    }

    public boolean addWall(Player player, DoubleWall wall)
    {
        if(getWallsRemaining(player) <= 0)
            return false;

        clientList.get(player.getUsername()).walls.add(wall);
        setWallsRemaining(player, getWallsRemaining(player) - 1);
        return true;
    }

    public List<DoubleWall> getWalls(Player player)
    {
        return clientList.get(player.getUsername()).walls;
    }

    /**
     * This method is to determind the winner of a game.
     * Still add for the Player Stats...
     * @param winner The player that is the Winner
     */
    public void wonGame(Player winner) {
        PlayerStats playerStats = winner.getPlayerStats();
        playerStats.wonGame();
    }

    /**
     * Determies the loser of the game
     * This adds to the stats of the player
     * @param loser Type player that lost the game
     */
    public void lostGame(Player loser) {
        PlayerStats playerStats = loser.getPlayerStats();
        playerStats.loseGame();
    }

    public Tile getStartPos (Player player){
        return null;
    }

    private class PlayerListData
    {
        boolean host = false;
        PlayerMarker marker = null;
        boolean mute = false;
        int team = 0;
        int wallsRemaining = 0;
        Player player;
        List<DoubleWall> walls = new ArrayList<>();

        public PlayerListData(Player player) {
            this.player = player;
        }

        public boolean isPlaying()
        {
            return marker != null;
        }
    }
}