package com.cs309.quoridorApp.dataPackets.toClient.chat;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.game.Game;
import com.cs309.quoridorApp.game.GameInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * A packet that holds the list of Strings that make up a game's
 * chat.
 *
 * Strings are in format "<username>:<message>".
 */
public class ReturnChatPacket extends ClientPacket
{

    private List<String> chatMessages = new ArrayList<>();

    public ReturnChatPacket(Game game)
    {
        chatMessages = GameInteractor.getChat(game);
    }

    public List<String> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<String> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
