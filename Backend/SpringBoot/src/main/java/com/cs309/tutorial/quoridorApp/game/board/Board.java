package com.cs309.tutorial.quoridorApp.game.board;

import com.cs309.tutorial.quoridorApp.game.Game;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.TileBound;
import com.cs309.tutorial.quoridorApp.game.board.tileBound.Wall;
import com.cs309.tutorial.quoridorApp.player.Player;

public class Board
{
    public final Game game;

    //x,y
    private final Tile[][] tiles;

    //NOTE TO SELF: ROW IS X AND COLUMN IS Y
    public Board(Game game)
    {
        this.game = game;
        int rowSize = game.getRules().getRowSize();
        int columnSize = game.getRules().getColumnSize();

        tiles = new Tile[rowSize][columnSize];
        for(int x = 0; x < rowSize; x++)
            for (int y = 0; y < columnSize; y++)
                tiles[x][y] = new Tile(x, y, this);
    }

    public int getRowSize() {
        return tiles.length;
    }

    public int getColumnSize() {
        return tiles[0].length;
    }

    public boolean withinBoard(int x, int y) {
        return y >= 0 && y < getColumnSize() && x >= 0 && x < getRowSize();
    }

    public Tile getTile(int x, int y) {
        if(withinBoard(x, y))
            return tiles[x][y];
        return null;
    }

    public boolean add(TileBound tb, Tile tile)
    {
        return tile.add(tb, Tile.Direction.NONE);
    }

    public boolean addPlayer(Player player, int x, int y)
    {
        PlayerMarker p = new PlayerMarker(player);
        if(player == null || !withinBoard(x,y) || !tiles[x][y].add(p, Tile.Direction.NONE))
            return false;
        return true;
    }

    public boolean addWall(Player player, int x1, int y1, int x2, int y2)
    {
        if(player != null && withinBoard(x1, y1) && withinBoard(x2, y2))
        {
            Tile t1 = tiles[x1][y1];
            Tile t2 = tiles[x2][y2];
            Wall wall = new Wall(player);

            return wall.setTile(t1, t2);
        }

        return false;
    }

    @Override
    public String toString() {
        String toReturn = "";
        for(int y = 0; y < getColumnSize(); y++)
        {
            String[] row = new String[]{"", "", ""};
            for(int x = 0; x < getRowSize(); x++)
            {
                String[] tile = getTile(x, y).separateStrings();
                row[0] += tile[0] + " ";
                row[1] += tile[1] + " ";
                row[2] += tile[2] + " ";
            }
            toReturn += row[0] + "\n" + row[1] + "\n" + row[2] + "\n\n";
        }
        return toReturn;
    }

    public byte[][] getBytes()
    {
        byte[][] toReturn = new byte[getRowSize()][getColumnSize()];
        for(int y = 0; y < getColumnSize(); y++)
        {
            for(int x = 0; x < getRowSize(); x++)
            {
                toReturn[x][y] = getTile(x, y).getByteFormat();
            }
        }
        return toReturn;
    }
}
