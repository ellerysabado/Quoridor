package com.cs309.quoridorApp.game;

import com.cs309.quoridorApp.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameChat {



    private List<String> chat;

    public GameChat(){
        chat = new ArrayList<>();
    }

    public List<String> getChat() {
        return chat;
    }

    public void setChat(List<String> chat) {
        this.chat = chat;
    }

    public void addChat(Player player, String line){
        chat.add(player.getUsername() + ": " + line);
    }

    public void addChat(String fullMessage){
        chat.add(fullMessage == null ? "" : fullMessage);
    }

//    public void clearChat(List<String> chat){
//        chat = null;
//    }

}