package com.cs309.quoridorApp.game.board.tileBound;

import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.game.board.Tile;

public class SingleWall extends Building
{

    public SingleWall(Player placer) {
        super(placer);
    }

    @Override
    public boolean preventsMovement(Entity entity, Tile tile, Tile.Direction dir) {
        return equals(tile.getBuilding(dir)) &&
                equals(tile.getAdjacent(dir).getBuilding(dir.getOpposite()));
    }

    @Override
    protected Tile getMainTile() {
        return null;
    }
}
