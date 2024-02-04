package com.cs309.quoridorApp.dataPackets.toServer.game;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Uses given player to place a wall at the given position with the
 * given direction.
 *
 * May return failure if player cannot place a wall at given position.
 */
public class PlaceWallPacket extends SessionGameCheckPacket
{

    private int x = -1;
    private int y = -1;
    private int direction = -1;

    public PlaceWallPacket()
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

        boolean isHorizontal = direction == 0;

        if(x < 0 || x >= getGame().getRules().getRowSize() || y < 0 || y >= getGame().getRules().getColumnSize())
            return new ResultPacket(false, "Tile Does not Exist");
        else if(direction < 0)
            return new ResultPacket(false, "Not Valid Wall Rotation");
        else if(!GameInteractor.canPlaceWall(getGame(), getPlayer(sessions), x, y, isHorizontal))
            return new ResultPacket(false, "Cannot Place Wall There");

        GameInteractor.placeWall(history, getGame(), getPlayer(sessions), x, y, isHorizontal);
        GameInteractor.endTurn(history, getGame(), getPlayer(sessions));

        return new ResultPacket(true, "");
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
