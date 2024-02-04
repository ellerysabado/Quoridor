package com.cs309.quoridorApp.game.board.tileBound;

import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.player.Player;

public class PlayerMarker extends Entity
{
    private Player player;

    private Tile startTile;

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

    public Tile getTile() {
        return getMainTile();
    }

    public Tile getStartTile() {
        return startTile;
    }
    public void setStartTitle(Tile startTile){
        this.startTile = startTile;
    }
}
