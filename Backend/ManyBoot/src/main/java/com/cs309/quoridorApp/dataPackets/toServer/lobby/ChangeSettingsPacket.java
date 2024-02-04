package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.game.GameRules;
import com.cs309.quoridorApp.player.BotPlayer;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

import java.util.List;

//TODO: ADD TO AS MORE SETTINGS

/**
 *  Allows given user to change the settings of the given game
 *  if they are the current host.
 *
 *  May return failure if given settings aren't valid
 */
public class ChangeSettingsPacket extends SessionGameCheckPacket
{

    private int player_count = -1;
    private int host = -1;
    private int botCount = -1;

    public ChangeSettingsPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
        setHostCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {

        GameRules r = getGame().getRules();
        PlayerList pl = getGame().getClientList();

        if(getGame().getGameBoard() != null)
            return new ResultPacket(false, "Cannot Change Settings After Game Has Started");
        else if(player_count != -1 && player_count != 2 && player_count != 4)
            return new ResultPacket(false, "Only 2-Player and 4-Player Games Are Allowed");
        else if(pl.getClients().size() > player_count)
            return new ResultPacket(false, "Cannot Lower Player Count; Kick Players Before Decreasing Player Count");
        else if(host >= 0 && pl.getClients().size() <= host)
            return new ResultPacket(false, "Player Does Not Exist to Make Host");
        else if(botCount >= 0 && (
                (player_count != -1 && (botCount - pl.getBots().size()) + pl.getClients().size() > player_count) ||
                        (player_count == -1 && (botCount - pl.getBots().size()) + pl.getClients().size() > r.getMaxPlayers())
        ))
            return new ResultPacket(false, "Cannot Add Any More Bots");


        GameInteractor.changeGameSettings(getGame(), this);

        return new ResultPacket(true, "");
    }

    public int getPlayer_count() {
        return player_count;
    }

    public void setPlayer_count(int player_count) {
        this.player_count = player_count;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public int getBotCount() {
        return botCount;
    }

    public void setBotCount(int botCount) {
        this.botCount = botCount;
    }
}
