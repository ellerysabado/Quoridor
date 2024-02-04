package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

import java.util.List;

/**
 *  Allows given user to kick a player from the given game
 *  if they are the current host.
 *
 *  May fail if player to be kicked is the host or doesn't exist
 */
public class KickPlayerPacket extends SessionGameCheckPacket
{
    private int player = 0;

    public KickPlayerPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setHostCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {

        PlayerList pl = getGame().getClientList();
        List<Player> ps = pl.getClients();

        if(ps.size() <= player)
            return new ResultPacket(false, "Player Does Not Exist");
        else if(pl.isHost(ps.get(player)))
            return new ResultPacket(false, "Cannot Kick Host");

        GameInteractor.removeClientFromGame(getGame(), pl.getClients().get(player));

        return new ResultPacket(true, "");
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}
