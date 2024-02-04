package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

public class SuperMovePacket extends SessionGameCheckPacket
{
    private int x;
    private int y;

    public SuperMovePacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
        setGameStartedCheck(true);
        setPlayingCheck(true);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX()
    {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        if(!getGame().getGameBoard().getTile(x, y).canAdd(getGame().getClientList().getPlayerMaker(getPlayer(sessions)), Tile.Direction.NONE))
                return new ResultPacket(false, "Could Not Get To New Position");

        GameInteractor.moveMarker(history, getGame(), getPlayer(sessions), x, y, Tile.Direction.NONE);

        return new ResultPacket(true, "");
    }
}
