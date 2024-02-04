package com.cs309.quoridorApp.dataPackets.toServer.game;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.game.ReturnBoardPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Returns given game's entire board
 */
public class GetBoardPacket extends SessionGameCheckPacket
{

    public GetBoardPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
        setGameStartedCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        return new ReturnBoardPacket(getGame());
    }
}
