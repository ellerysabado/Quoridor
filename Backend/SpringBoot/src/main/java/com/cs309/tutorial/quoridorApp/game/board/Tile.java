package com.cs309.tutorial.quoridorApp.game.board;

import com.cs309.tutorial.quoridorApp.game.board.tileBound.Building;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.Entity;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.TileBound;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    public int x;
    public int y;

    private Board board;

    private List<Entity> entities = new ArrayList<>();
    private Building[] buildings = new Building[Direction.values().length];

    protected Tile(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public List<Entity> getEntities()
    {
        return entities;
    }

    public Building[] getBuildings()
    {
        return buildings;
    }

    public Building getBuilding(Direction dir)
    {
        return buildings[dir.ordinal()];
    }

    public boolean canAdd(TileBound tb, Direction dir)
    {
        if(tb == null)
            return false;
        else if(tb instanceof Building)
            return canAdd((Building) tb, dir);
        else if(tb instanceof Entity)
            return canAdd((Entity) tb, dir);
        return false;
    }

    private boolean canAdd(Building building, Direction dir)
    {
        boolean toReturn = true;
        for(Building b: buildings)
            toReturn = toReturn && (b == null || !b.preventsBuilding(building, this, dir));
        return toReturn;
    }

    private boolean canAdd(Entity entity, Direction dir)
    {
        for(Entity e: entities)
            if(e != null && e.preventsMovement(entity, this, dir))
                return false;
        for(Building b: buildings)
            if(b != null && b.preventsMovement(entity, this, dir))
                return false;

        return true;
    }

    public boolean add(TileBound tb, Direction dir)
    {
        if(tb == null)
            return false;
        else if(tb instanceof Building)
            return add((Building) tb, dir);
        else if(tb instanceof Entity)
            return add((Entity) tb, dir);
        return false;
    }

    private boolean add(Building building, Direction dir)
    {
        if(canAdd(building, dir))
        {
            buildings[dir.ordinal()] = building;
            return true;
        }
        return false;
    }

    private boolean add(Entity entity, Direction dir)
    {
        if(canAdd(entity, dir))
        {
            entities.add(entity);
            return true;
        }
        return false;
    }

    public Tile getAdjacent(Direction dir)
    {
        return board.getTile(x + dir.xChange, y + dir.yChange);
    }

    public Direction getAdjacency(Tile other)
    {
        for(Direction dir: Direction.values())
            if(other == getAdjacent(dir) && this == other.getAdjacent(dir.getOpposite()))
                return dir;
        return Direction.NONE;
    }

    public boolean isAdjacent(Tile other) {
        for(Direction dir: Direction.values())
            if(dir != Direction.NONE && other == getAdjacent(dir) && this == other.getAdjacent(dir.getOpposite()))
                return true;
        return false;
    }

    public double getDistance(Tile other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public int hashCode()
    {
        return y * board.getRowSize() + x;
    }

    @Override
    public String toString()
    {
        String toReturn = "";
        for(String string : separateStrings())
            toReturn += string + "\n";
        return (toReturn);
    }

    public String[] separateStrings()
    {
        String[] toReturn = new String[3];
        toReturn[0] = getBuilding(Direction.UP) == null ? "" : "---";
        toReturn[1] = (getBuilding(Direction.LEFT) == null ? " " : "[") +
                getEntities().size() +
                (getBuilding(Direction.RIGHT) == null ? " " : "]");
        toReturn[2] = getBuilding(Direction.UP) == null ? "" : "---";
        return toReturn;
    }

    public byte getByteFormat()
    {
        byte toReturn = 0b0;
        if(getBuilding(Direction.UP) != null)
            toReturn += 0b1;
        if(getBuilding(Direction.RIGHT) != null)
            toReturn += 0b10;
        if(getBuilding(Direction.DOWN) != null)
            toReturn += 0b100;
        if(getBuilding(Direction.LEFT) != null)
            toReturn += 0b1000;

        boolean player = false;
        boolean other = false;
        for(Entity entity: getEntities())
        {
            if(!player && entity instanceof PlayerMarker) {
                player = true;
                toReturn += board.game.getClientList().getTeam(((PlayerMarker) entity).getPlayer()) << 4;
            }
            else if(!other && player && entity instanceof  PlayerMarker)
            {
                other = true;
                toReturn += 0b10000000;
            }
            else if(!other && !(entity instanceof PlayerMarker))
            {
                other = true;
                toReturn += 0b10000000;
            }
        }
        return toReturn;
    }

    public enum Direction
    {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0),
        NONE(0, 0);

        int xChange;
        int yChange;

        Direction(int x, int y) {
            this.xChange = x;
            this.yChange = y;
        }

        public Direction getOpposite() {
            switch (this)
            {
                case UP:
                    return DOWN;
                case DOWN:
                    return UP;
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
            }
            return NONE;
        }
    }
}
