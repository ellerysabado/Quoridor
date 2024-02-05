package com.cs309.tutorial.quoridorApp.game.board.tileBound;

import com.cs309.tutorial.quoridorApp.game.board.Tile;
import com.cs309.tutorial.quoridorApp.player.Player;

public class PlayerMarker extends Entity
{
    private Player player;

    public PlayerMarker(Player player)
    {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean move(Tile tile, Tile.Direction dir) {
        return tile.isAdjacent(getMainTile()) &&
                setTile(tile, dir);
    }
}
