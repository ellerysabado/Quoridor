package com.cs309.quoridorApp.game.board.tileBound;

import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.player.Player;

public class Goal extends Building
{

    protected Goal(Player placer) {
        super(placer);
    }

    @Override
    public boolean preventsMovement(Entity entity, Tile tile, Tile.Direction dir) {
        return false;
    }

    @Override
    protected Tile getMainTile() {
        return null;
    }
}
