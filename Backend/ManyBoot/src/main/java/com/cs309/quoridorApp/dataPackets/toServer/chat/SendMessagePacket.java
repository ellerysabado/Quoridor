package com.cs309.quoridorApp.dataPackets.toServer.chat;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.dataPackets.toServer.SessionGameCheckPacket;
import com.cs309.quoridorApp.game.GameInteractor;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Sends single message to server
 */
public class SendMessagePacket extends SessionGameCheckPacket
{
    private String chatMessage = "";

    public SendMessagePacket()
    {
        setSessionCheck(true);
        setGameCheck(true);
        setInGameCheck(true);
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history) {
        GameInteractor.addChatMessage(getGame(), getPlayer(sessions), chatMessage);

        return new ResultPacket(true, "");
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
