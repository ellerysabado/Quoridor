package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.PacketNotFoundPacket;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Default packet, if SuperPacket function does not correspond to
 * a packet class type
 */
public class UnregisteredPacket extends ServerPacket
{
    @Override
    public ClientPacket execute(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        return new PacketNotFoundPacket();
    }
}
