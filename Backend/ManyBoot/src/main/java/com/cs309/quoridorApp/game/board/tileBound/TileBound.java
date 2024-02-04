package com.cs309.quoridorApp.game.board.tileBound;

import com.cs309.quoridorApp.game.board.Tile;

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

    /**
     * This is the function for returning the tile that the player it on
     * @return getMainTile, which is the location of the playerMarker
     */
    public Tile getTile() {
        return getMainTile();
    }

    /**
     * Abstract method to get the main tile
     * @return title, location of playermarker
     */
    protected abstract Tile getMainTile();
}