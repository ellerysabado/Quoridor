package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ListPacket;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Returns list of given user's friends
 */
public class CheckFriendsPacket extends SessionGameCheckPacket{

    public CheckFriendsPacket()
    {
        setSessionCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        return new ListPacket<Player>(getPlayer(sessions).getFriends());
    }
}
