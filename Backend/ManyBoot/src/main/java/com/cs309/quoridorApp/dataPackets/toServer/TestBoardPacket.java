package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.game.Game;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

public class TestBoardPacket extends ServerPacket
{


    @Override
    public ClientPacket execute(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {

        Player p = getPlayer(sessions);
        Game g = GameInteractor.CreateGame();
        GameInteractor.addClientToGame(g, p);
        GameInteractor.setHost(g, p);

        GameInteractor.startGame(getGame(), players, history, stats);

        return null;
    }
}
