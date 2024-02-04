package com.cs309.quoridorApp.dataPackets.toServer.chat;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.chat.ReturnChatPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Returns full game chat of given game
 */
public class GetChatPacket extends SessionGameCheckPacket
{

    public GetChatPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        return new ReturnChatPacket(getGame());
    }
}
