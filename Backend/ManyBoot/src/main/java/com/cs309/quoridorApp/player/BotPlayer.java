package com.cs309.quoridorApp.player;

public class BotPlayer extends Player
{

    public BotPlayer(String username)
    {
        setUsername(username);
    }

    public boolean moveAct()
    {
        return false;
    }
}
