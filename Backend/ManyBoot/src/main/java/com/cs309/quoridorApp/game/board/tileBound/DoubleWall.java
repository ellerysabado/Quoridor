package com.cs309.quoridorApp.game.board.tileBound;

import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.player.BotPlayer;
import com.cs309.quoridorApp.player.Player;

import java.util.Objects;

public class DoubleWall  extends Building
{

    public DoubleWall(Player placer) {
        super(placer);
    }



    
    private String directionOfWall;

    public boolean setTile(Tile tile, boolean isHorizontal)
    {
        if(isHorizontal)
        {
            directionOfWall = "Horizontal";

            Tile t1 = tile;
            Tile t2 = t1.getAdjacent(Tile.Direction.DOWN);

            Tile t3 = t1.getAdjacent(Tile.Direction.RIGHT);
            Tile t4 = t3.getAdjacent(Tile.Direction.DOWN);

            if(!validPlacement(t1, t2) || !validPlacement(t3, t4))
                return false;

            setTile(t1, Tile.Direction.DOWN);
            setTile(t2, Tile.Direction.UP);

            setTile(t3, Tile.Direction.DOWN);
            setTile(t4, Tile.Direction.UP);
            return true;
        }
        else
        {

            directionOfWall = "Vertical";

            Tile t1 = tile;
            Tile t2 = t1.getAdjacent(Tile.Direction.RIGHT);

            Tile t3 = t1.getAdjacent(Tile.Direction.DOWN);
            Tile t4 = t3.getAdjacent(Tile.Direction.RIGHT);

            if(!validPlacement(t1, t2) || !validPlacement(t3, t4))
                return false;

            setTile(t1, Tile.Direction.RIGHT);
            setTile(t2, Tile.Direction.LEFT);

            setTile(t3, Tile.Direction.RIGHT);
            setTile(t4, Tile.Direction.LEFT);

            return true;
        }
    }

    public static boolean isValidPlacement(Tile tile, boolean isHorizontal) {

        Tile t1 = tile;
        if(t1 == null)
            return false;
        Tile t2 = t1.getAdjacent(Tile.Direction.DOWN);
        if(t2 == null)
            return false;

        Tile t3 = t1.getAdjacent(Tile.Direction.RIGHT);
        if(t3 == null)
            return false;
        Tile t4 = t3.getAdjacent(Tile.Direction.DOWN);
        if(t4 == null)
            return false;

        if (!isHorizontal) {
            t2 = t1.getAdjacent(Tile.Direction.RIGHT);
            if(t2 == null)
                return false;

            t3 = t1.getAdjacent(Tile.Direction.DOWN);
            if(t3 == null)
                return false;
            t4 = t3.getAdjacent(Tile.Direction.RIGHT);
            if(t4 == null)
                return false;
        }

        DoubleWall wall = new DoubleWall(new BotPlayer("placer"));
        if(!wall.validPlacement(t1, t2) || !wall.validPlacement(t3, t4))
            return false;

        return true;
    }
    public String getDirectionOfWall() {
        return directionOfWall;
    }

    public void setDirectionOfWall(String directionOfWall) {
        this.directionOfWall = directionOfWall;
    }

    @Override
    public boolean preventsMovement(Entity entity, Tile tile, Tile.Direction dir) {
        return equals(tile.getBuilding(dir)) &&
                equals(tile.getAdjacent(dir).getBuilding(dir.getOpposite()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleWall that = (DoubleWall) o;
        return Objects.equals(directionOfWall, that.directionOfWall) && that.getMainTile().equals(getMainTile());
    }

    @Override
    protected Tile getMainTile() {
        Tile toReturn = null;
        for(Tile tile : getTiles())
            if(toReturn == null || tile.x + tile.y < toReturn.x + toReturn.y  || (tile.x + tile.y == toReturn.x + toReturn.y && tile.x < toReturn.x))
                toReturn = tile;
        return toReturn;
    }
}
