package com.cs309.tutorial.quoridorApp.game.board.tileBound;

import com.cs309.tutorial.quoridorApp.game.board.Tile;
import com.cs309.tutorial.quoridorApp.player.Player;

public class Wall extends Building
{

    public Wall(Player placer) {
        super(placer);
    }

    @Override
    public boolean preventsMovement(Entity entity, Tile tile, Tile.Direction dir) {
        return tile.getBuilding(dir) == this &&
                tile.getAdjacent(dir).getBuilding(dir.getOpposite()) == this;
    }
}
