package com.cs309.quoridorApp.dataPackets.toServer.lobby;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.lobby.ReturnStatePacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Returns the state of the lobby
 */
public class GetStatePacket extends SessionGameCheckPacket
{

    public GetStatePacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        ReturnStatePacket toReturn = new ReturnStatePacket();
        toReturn.setLobby_state(1);
        if(getGame().getWinner() > 0)
            toReturn.setLobby_state(2);
        else if(getGame().getGameBoard() != null)
            toReturn.setLobby_state(0);
        return toReturn;
    }
}
