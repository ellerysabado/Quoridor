package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.InvalidGamePacket;
import com.cs309.quoridorApp.dataPackets.toClient.InvalidSessionPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Is an abstract sub-packet used to quickly check common sub-packet
 * requirements, which are toggled in their constructor.
 *
 * Replaces execute() with afterCheck() as method required to be
 * overridden; automatically returns failure if toggled requirements
 * are not met.
 */
public abstract class SessionGameCheckPacket extends ServerPacket
{

    boolean sessionCheck = false;
    boolean gameCheck = false;
    boolean inGameCheck = false;
    boolean isHostCheck = false;
    boolean gameStartedCheck = false;
    boolean isPlayingCheck = false;
    boolean isTurnCheck = false;

    public boolean isSessionCheck() {
        return sessionCheck;
    }

    public void setSessionCheck(boolean sessionCheck) {
        this.sessionCheck = sessionCheck;
    }

    public void setGameCheck(boolean gameCheck) {
        this.gameCheck = gameCheck;
    }

    public boolean isGameCheck() {
        return gameCheck;
    }

    public void setInGameCheck(boolean inGameCheck) {
        this.inGameCheck = inGameCheck;
    }

    public boolean isInGameCheck() {
        return inGameCheck;
    }

    public void setHostCheck(boolean hostCheck) {
        isHostCheck = hostCheck;
    }

    public boolean isHostCheck() {
        return isHostCheck;
    }

    public void setGameStartedCheck(boolean gameStartedCheck) {
        this.gameStartedCheck = gameStartedCheck;
    }

    public boolean isGameStartedCheck() {
        return gameStartedCheck;
    }

    public void setPlayingCheck(boolean playingCheck) {
        isPlayingCheck = playingCheck;
    }

    public boolean isPlayingCheck() {
        return isPlayingCheck;
    }

    public void setTurnCheck(boolean turnCheck) {
        isTurnCheck = turnCheck;
    }

    public boolean isTurnCheck() {
        return isTurnCheck;
    }

    @Override
    public ClientPacket execute(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {

        if(sessionCheck && !validSession(sessions))
            return new InvalidSessionPacket();
        else if(gameCheck && !validGame())
            return new InvalidGamePacket();
        else if(inGameCheck && !inGame(sessions))
            return new ResultPacket(false, "Not In Game");
        else if(isHostCheck && !getGame().getClientList().isHost(getPlayer(sessions)))
            return new ResultPacket(false, "Not Host");
        else if(gameStartedCheck && getGame().getGameBoard() == null)
            return new ResultPacket(false, "Game Not Started");
        else if(isPlayingCheck && !getGame().getClientList().isPlaying(getPlayer(sessions)))
            return new ResultPacket(false, "Not A Player");
        else if(isTurnCheck && (getGame().getClientList().getCurrentPlayer() == null || getGame().isGameOver() || !getGame().getClientList().getCurrentPlayer().equals(getPlayer(sessions))))
            return new ResultPacket(false, "Not Your Turn");

        return afterCheck(players, sessions, stats, history);
    }

    public abstract ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history);
}
