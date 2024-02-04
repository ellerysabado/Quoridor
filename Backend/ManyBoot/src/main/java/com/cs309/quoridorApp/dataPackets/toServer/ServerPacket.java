package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.Packet;
import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.game.Game;
import com.cs309.quoridorApp.game.GameHolder;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Abstract sub-packet that contains common variables uuid and session,
 * corresponding to the user's session UUID and the game's ID
 * respectively.
 *
 * Contains a few methods to access information quickly, such as
 * getting the player via their session UUID.
 *
 * All sub-packets will override execute() in some way, which is
 * called in order to perform the packets' duty and return a
 * ClientPacket.
 *
 * In addition, some packets may contain extra variables and their
 * getters and setters based on extra data they require.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ServerPacket extends Packet
{
    private String uuid = "";
    private String session = "AAAAAA";

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public Player getPlayer(SessionRepository sessions)
    {
        return sessions.getPlayer(uuid);
    }

    public boolean validSession(SessionRepository sessions)
    {
        return sessions.existsById(uuid);
    }

    public Game getGame()
    {
        return GameHolder.getGame(session);
    }

    public boolean validGame()
    {
        return GameHolder.inSession(session);
    }

    public boolean inGame(SessionRepository sessions)
    {
        return validSession(sessions) && validGame() &&
                getGame().getClientList().isClient(getPlayer(sessions));
    }
    public abstract ClientPacket execute(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history);

}
