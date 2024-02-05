package com.cs309.tutorial.quoridorApp.game;

import com.cs309.tutorial.quoridorApp.game.board.Board;
import com.cs309.tutorial.quoridorApp.game.board.Tile;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.tutorial.quoridorApp.player.Player;

public class GameRules
{
    /*
    alternate rules for the game
     */
    private final Game game;

    private int rowSize;
    private int columnSize;

    //default game rules
    public GameRules(Game game)
    {
        this.game = game;
        rowSize = 9;
        columnSize = 9;
    }

    public int getRowSize()
    {
        return rowSize;
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    public PlayerMarker initPlayer(Player player)
    {
        PlayerMarker pm = new PlayerMarker(player);
        Board b = game.getGameBoard();

        boolean addedPlayer = false;
        while(!addedPlayer) {
            addedPlayer = pm.setTile(
                    b.getTile((int)(Math.random() * b.getRowSize()), (int)(Math.random() * b.getColumnSize())),
                    Tile.Direction.NONE
            );
        }

        return pm;
    }
}
