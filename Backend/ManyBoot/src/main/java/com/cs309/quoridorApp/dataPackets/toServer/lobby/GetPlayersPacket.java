package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.ListedPlayer;
import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.lobby.ReturnPlayersPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns list of players currently in given game.
 */
public class GetPlayersPacket extends SessionGameCheckPacket
{

    public GetPlayersPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        List<Player> clients = getGame().getClientList().getClients();
        List<ListedPlayer> toReturn = new ArrayList<>();

        for(Player player: clients)
            toReturn.add(new ListedPlayer(getGame(), player));

        return new ReturnPlayersPacket(toReturn);
    }
}
