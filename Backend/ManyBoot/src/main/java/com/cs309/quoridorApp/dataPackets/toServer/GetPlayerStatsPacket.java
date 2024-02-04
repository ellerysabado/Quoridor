package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.PlayerStatsPacket;
import com.cs309.quoridorApp.dataPackets.toClient.RankPacket;
import com.cs309.quoridorApp.player.PlayerStats;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

public class GetPlayerStatsPacket extends SessionGameCheckPacket{
    public GetPlayerStatsPacket() {
        setSessionCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        PlayerStats playerStats = stats.getOne(getPlayer(sessions).getUsername());
        return new PlayerStatsPacket(playerStats.getId(),playerStats.getWins(),playerStats.getLosses());
    }
}