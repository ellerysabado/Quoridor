package com.cs309.tutorial.quoridorApp.game.board.tileBound;

import com.cs309.tutorial.quoridorApp.game.board.Tile;

import java.util.ArrayList;
import java.util.List;

public abstract class TileBound {
    protected List<Tile> tiles = new ArrayList<>();

    //currently dir is direction that the TB is coming in from
    public boolean setTile(Tile tile, Tile.Direction dir)
    {
        if(!tile.canAdd(this, dir.getOpposite()))
            return false;

        remove();
        tiles.add(tile);
        tile.add(this, dir);
        return true;
    }

    public void remove()
    {
        for(Tile tile : tiles)
        {
            List<Entity> entities = new ArrayList<>(tile.getEntities());
            for(Entity entity : entities)
                if(this == entity)
                    tile.getEntities().remove(entity);
            for(int i = 0; i < tile.getBuildings().length; i++)
                if(this == tile.getBuildings()[i])
                    tile.getBuildings()[i] = null;
        }
        tiles.clear();
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}