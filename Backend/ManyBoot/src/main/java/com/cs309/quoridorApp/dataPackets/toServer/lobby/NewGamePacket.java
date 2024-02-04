package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.lobby.ReturnNewGamePacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.Game;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Creates and returns a new game, setting the given user
 * as the host of the game
 */
public class NewGamePacket extends SessionGameCheckPacket
{
    public NewGamePacket()
    {
        setSessionCheck(true);
    }


    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        Player p = getPlayer(sessions);
        Game g = GameInteractor.CreateGame();
        GameInteractor.addClientToGame(g, p);
        GameInteractor.setHost(g, p);

        return new ReturnNewGamePacket(g);
    }
}
