package com.cs309.quoridorApp.game.board.tileBound;

import com.cs309.quoridorApp.game.board.Tile;

public abstract class Entity extends TileBound
{
    /*
    things that are going to be *inside* the tiles, like players, monsters, or traps
     */

    public boolean preventsMovement(Entity other, Tile tile, Tile.Direction dir)
    {
        return true;
    }


    public Tile getMainTile()
    {
        return tiles.size() > 0 ? tiles.get(0) : null;
    }

    public int getX()
    {
        return getMainTile().x;
    }

    public int getY()
    {
        return getMainTile().y;
    }
    public abstract boolean move(Tile tile, Tile.Direction dir);

}
