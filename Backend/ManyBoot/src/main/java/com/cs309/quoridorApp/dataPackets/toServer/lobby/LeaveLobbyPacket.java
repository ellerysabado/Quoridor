package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Removes given player from given game
 */
public class LeaveLobbyPacket extends SessionGameCheckPacket
{

    public LeaveLobbyPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {

        GameInteractor.removeClientFromGame(getGame(), getPlayer(sessions));

        return new ResultPacket(true, "");
    }
}
