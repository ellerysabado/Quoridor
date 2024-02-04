package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.lobby.ReturnSettingsPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Returns settings of given game
 */
public class GetSettingsPacket extends SessionGameCheckPacket
{

    public GetSettingsPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        return new ReturnSettingsPacket(getGame());
    }
}
