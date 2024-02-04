package com.cs309.quoridorApp.dataPackets.toServer.game;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.game.board.Board;
import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Moves given player up to two spaces away.
 *
 * May return failure if player cannot move to given position.
 */
public class MovePlayerPacket extends SessionGameCheckPacket
{
    private int x1 = -1;
    private int y1 = -1;
    private int x2 = -1;
    private int y2 = -1;


    public MovePlayerPacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
        setGameStartedCheck(true);
        setPlayingCheck(true);
        setTurnCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        int x = x1;
        int y = y1;
        Player p = getPlayer(sessions);
        PlayerMarker pm = getGame().getClientList().getPlayerMaker(p);
        Board b = getGame().getGameBoard();

        Tile.Direction d1 = pm.getTile().getAdjacency(b.getTile(x, y));
        Tile.Direction d2 = Tile.Direction.NONE;
        if(x2 >= 0 && y2 >= 0)
        {
            x = x2;
            y = y2;

            d2 = b.getTile(x1, y2).getAdjacency(b.getTile(x, y));
        }


        if(x1 < 0 || x1 >= getGame().getRules().getRowSize() || y1 < 0 || y1 >= getGame().getRules().getColumnSize())
            return new ResultPacket(false, "Tile Does not Exist");
        else if(!GameInteractor.canMove(getGame(), p, x, y, d1, d2))
            return new ResultPacket(false, "Cannot Move There");

        GameInteractor.moveMarker(history, getGame(), p, x, y,
                d2 == Tile.Direction.NONE ? d1 : d2);
        GameInteractor.endTurn(history, getGame(), getPlayer(sessions));

        return new ResultPacket(true, "");
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
}