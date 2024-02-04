package com.cs309.quoridorApp.dataPackets.websockets;

public class UpdateChatPacket extends WebsocketPacket {

    private String chatMessage;

    public UpdateChatPacket(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
