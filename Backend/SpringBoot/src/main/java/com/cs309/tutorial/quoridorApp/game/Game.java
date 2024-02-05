package com.cs309.tutorial.quoridorApp.game;

import com.cs309.tutorial.quoridorApp.game.board.Board;
import com.cs309.tutorial.quoridorApp.player.PlayerList;

public class Game
{
    /*
    used to hold the game's board, players / spectators, chat, history,
    and whatever else a game needs
     */

    private static int totalGames = 0;
    public int id = totalGames++;

    private PlayerList clientList = new PlayerList(this);
    //private Chat chat;

    private GameRules rules = new GameRules(this);
    private GameInfo info;
    private Board gameBoard;

    public void startGame()
    {
        gameBoard = new Board(this);
    }

    public Board getGameBoard()
    {
        return gameBoard;
    }

    public GameRules getRules()
    {
        return rules;
    }

    public PlayerList getClientList()
    {
        return clientList;
    }
}
