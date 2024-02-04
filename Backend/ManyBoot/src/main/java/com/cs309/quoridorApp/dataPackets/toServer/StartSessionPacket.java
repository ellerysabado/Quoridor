package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.NewSessionPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerSession;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;


/**
 * Generates and returns a new session, if given data is valid
 */
public class StartSessionPacket extends ServerPacket
{

    private boolean login;
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public ClientPacket execute(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history)
    {
        Player p = null;
        if(players.existsById(username))
            p = players.getOne(username);

        if(isLogin())
        {
            if(p == null || !p.getPassword().equals(password))
                return new ResultPacket(false, "Invalid Username or Password");
            PlayerSession ps = new PlayerSession(p);
            sessions.save(ps);
            return new NewSessionPacket(ps.getId());
        }
        else
        {
            if(p != null)
                return new ResultPacket(false, "Username is Taken");
            p = new Player(username, password);
            stats.save(p.getPlayerStats());
            players.save(p);

            PlayerSession ps = new PlayerSession(p);
            sessions.save(ps);
            return new NewSessionPacket(ps.getId());
        }
    }
}
