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
 * Puts given user into lobby.
 *
 * May fail if the user is already in the game or if game is full.
 */
public class JoinLobbyPacket extends SessionGameCheckPacket
{
    public JoinLobbyPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {

        PlayerList pl = getGame().getClientList();

        if(pl.isClient(getPlayer(sessions)))
            return new ResultPacket(false, "Already In Game");
        else if(getGame().getRules().getMaxPlayers() <= pl.getClients().size())
            return new ResultPacket(false, "Game Is Full");

        GameInteractor.addClientToGame(getGame(), getPlayer(sessions));

        return new ResultPacket(true, "");
    }
}
