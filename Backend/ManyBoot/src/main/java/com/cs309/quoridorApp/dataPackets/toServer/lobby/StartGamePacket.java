package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.player.PlayerList;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Allows given user to start the given game
 * if they are the current host.
 *
 * May fail if the game has already started or there aren't the right
 * amount of players in game.
 */
public class StartGamePacket extends SessionGameCheckPacket
{

    public StartGamePacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
        setHostCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        PlayerList pl = getGame().getClientList();

        if(getGame().getGameBoard() != null)
            return new ResultPacket(false, "Game Already Started");
        else if(pl.getClients().size() < getGame().getRules().getMaxPlayers())
            return new ResultPacket(false, "Not Enough Players; " +
                    getGame().getRules().getMaxPlayers() + " Players Required");
        else if(pl.getClients().size() > getGame().getRules().getMaxPlayers())
            return new ResultPacket(false, "Too Many Players; howd that happen???");

        GameInteractor.startGame(getGame(), players, history, stats);
        return new ResultPacket(true, "");
    }
}
