package com.cs309.tutorial.quoridorApp.game.board.tileBound;

import com.cs309.tutorial.quoridorApp.game.board.Tile;
import com.cs309.tutorial.quoridorApp.player.Player;

public abstract class Building extends TileBound
{
    /*
    acts as the buildings between tiles
    extended by wall and added for future expansion
     */
    private final Player placer;

    protected Building(Player placer)
    {
        this.placer = placer;
    }

    public Player getPlacer()
    {
        return placer;
    }

    //dir is where its being built on tile
    public boolean setTile(Tile tile, Tile.Direction dir)
    {
        if(!validPlacement(tile, dir))
            return false;

        tiles.add(tile);

        tile.add(this, dir);
        return true;
    }

    public boolean setTile(Tile t1, Tile t2)
    {
        if(!validPlacement(t1, t2))
            return false;

        setTile(t1, t1.getAdjacency(t2));
        setTile(t2, t2.getAdjacency(t1));

        return true;
    }

    public boolean validPlacement(Tile tile, Tile.Direction dir)
    {
        return tile.canAdd(this, dir);
    }

    public boolean validPlacement(Tile t1, Tile t2)
    {
        return t1.isAdjacent(t2) &&
                validPlacement(t1, t1.getAdjacency(t2)) &&
                validPlacement(t2, t2.getAdjacency(t1));
    }

    public boolean preventsBuilding(Building other, Tile tile, Tile.Direction dir)
    {
        return tile.getBuilding(dir) == this ||
                tile.getAdjacent(dir).getBuilding(dir.getOpposite()) == this;
    }

    public abstract boolean preventsMovement(Entity entity, Tile tile, Tile.Direction dir);
}
