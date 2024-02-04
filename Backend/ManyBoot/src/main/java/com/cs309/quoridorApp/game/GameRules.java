package com.cs309.quoridorApp.game;

import com.cs309.quoridorApp.game.board.Board;
import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameRules
{
    /*
    alternate rules for the game
     */
    private final Game game;

    private int rowSize;
    private int columnSize;

    private int bots;

    private int maxPlayers;

    private int maxWalls;

    //default game rules
    public GameRules(Game game)
    {
        this.game = game;
        rowSize = 9;
        columnSize = 9;
        bots = 0;
        maxPlayers = 2;
        maxWalls = 10;
    }

    public int getMaxWalls() {
        return maxWalls;
    }

    public void setMaxWalls(int maxWalls) {
        this.maxWalls = maxWalls;
    }

    public void setBots(int bots) {
        this.bots = bots;
    }

    public int getBots() {
        return bots;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getRowSize()
    {
        return rowSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public PlayerMarker initPlayer(Player player)
    {
        PlayerMarker pm = new PlayerMarker(player);
        Board b = game.getGameBoard();
        PlayerList pl = game.getClientList();

        int temp = 1;
        while(pl.getTeam(player) == 0)
        {
            if(pl.getTeamMembers(temp).size() == 0)
                pl.setTeam(player, temp);
            temp++;
        }

        temp--;

        boolean addedPlayer = false;

        if(temp == 1)
            addedPlayer = pm.setTile(b.getTile(0, getColumnSize()/2), Tile.Direction.NONE);

        else if(temp == 2)
            addedPlayer = pm.setTile(b.getTile(getRowSize() - 1, getColumnSize()/2), Tile.Direction.NONE);
        else if(temp == 3)
            addedPlayer = pm.setTile(b.getTile(getRowSize()/2, getColumnSize() - 1), Tile.Direction.NONE);
        else if(temp == 4)
            addedPlayer = pm.setTile(b.getTile(getRowSize()/2, 0), Tile.Direction.NONE);

        while(!addedPlayer) {
            addedPlayer = pm.setTile(
                    b.getTile((int)(Math.random() * b.getRowSize()), (int)(Math.random() * b.getColumnSize())),
                    Tile.Direction.NONE
            );
        }

        return pm;
    }

    public boolean canMoveThere(Tile tile, Player player, Tile.Direction... dir){
        PlayerMarker playerMarker = game.getClientList().getPlayerMaker(player);
        Tile.Direction up = Tile.Direction.UP;
        Tile.Direction down = Tile.Direction.DOWN;
        Tile.Direction right = Tile.Direction.RIGHT;
        Tile.Direction left = Tile.Direction.LEFT;

        if(playerMarker == null){ //if not client then return false
            return false;
        }

        ArrayList<Tile.Direction> direction = new ArrayList<>();
        for (int i = 0; i < dir.length; i++){
            direction.add(dir[i]);
            System.out.println(dir[i]);
        }

        if(direction.get(0) == Tile.Direction.UP) {
            if (direction.get(1) == Tile.Direction.NONE) { //want to move forward w/ nothing in front and only one move
                if (tile.getRow() < game.getGameBoard().getRowSize()) { //check out of bounds
                    if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.DOWN)) { //check if wall in front, want false
                        if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.DOWN)) { // check if another player marker, want false
                            return true; //true of there is not wall in front and no player marker
                        }
                    }
                }
            } else { //if there is a second move to move pass the front pawn
                if (direction.get(1) == Tile.Direction.UP) { //another move is up pass pawn
                    if (tile.getRow() < game.getGameBoard().getRowSize()) { //check out of bounds
                        if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.DOWN)) { //check if wall in front, want false
                            if (!tile.getAdjacent(Tile.Direction.DOWN).canAddOnlyEntities(playerMarker, Tile.Direction.DOWN)) { // check if another player marker in the first tile (want true)
                                if (tile.getAdjacent(Tile.Direction.DOWN).canAddOnlyBuildings(playerMarker, Tile.Direction.DOWN)) { // Make sure no wall in first front tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.DOWN)) { //No player on second tile already, want false
                                        return true; //true if jump spaces twice
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.RIGHT) { //move right diagonal
                    if (tile.getRow() < game.getGameBoard().getRowSize() || tile.getCol() < game.getGameBoard().getColumnSize()) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.LEFT).canAddOnlyEntities(playerMarker, Tile.Direction.UP)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.LEFT).canAddOnlyBuildings(playerMarker, Tile.Direction.UP)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.LEFT)) { //check if wall Left of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.LEFT) { //move left diagonal
                    if (tile.getRow() < game.getGameBoard().getRowSize() || tile.getCol() >= 0) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.RIGHT).canAddOnlyEntities(playerMarker, Tile.Direction.UP)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.RIGHT).canAddOnlyBuildings(playerMarker, Tile.Direction.UP)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.RIGHT)) { //check if wall Left of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.RIGHT)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else if (direction.get(0) == Tile.Direction.DOWN){
            if (direction.get(1) == Tile.Direction.NONE) { //want to move forward w/ nothing in front and only one move
                if (tile.getRow() >= 0) { //check out of bounds
                    if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.UP)) { //check if wall in front, want false
                        if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.UP)) { // check if another player marker, want false
                            return true; //true of there is not wall in front and no player marker
                        }
                    }
                }
            } else { //if there is a second move to move pass the front pawn
                if (direction.get(1) == Tile.Direction.DOWN) { //another move is up pass pawn
                    if (tile.getRow() >= 0) { //check out of bounds
                        if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.UP)) { //check if wall in front, want false
                            if (!tile.getAdjacent(Tile.Direction.UP).canAddOnlyEntities(playerMarker, Tile.Direction.UP)) { // check if another player marker in the first tile (want true)
                                if (tile.getAdjacent(Tile.Direction.UP).canAddOnlyBuildings(playerMarker, Tile.Direction.UP)) { // Make sure no wall in first front tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.UP)) { //No player on second tile already, want false
                                        return true; //true if jump spaces twice
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.RIGHT) { //move Down then right
                    if (tile.getRow() >= 0 || tile.getCol() < game.getGameBoard().getColumnSize()) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.LEFT).canAddOnlyEntities(playerMarker, Tile.Direction.DOWN)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.LEFT).canAddOnlyBuildings(playerMarker, Tile.Direction.DOWN)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.LEFT)) { //check if wall Left of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.LEFT) { //move left diagonal
                    if (tile.getRow() >= 0 || tile.getCol() >= 0) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.RIGHT).canAddOnlyEntities(playerMarker, Tile.Direction.DOWN)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.RIGHT).canAddOnlyBuildings(playerMarker, Tile.Direction.DOWN)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.RIGHT)) { //check if wall Left of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.RIGHT)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else if (direction.get(0) == Tile.Direction.RIGHT) {
            System.out.println("worked1");
            if (direction.get(1) == Tile.Direction.NONE) { //want to move forward w/ nothing in front and only one move
                System.out.println("worked2");
                if (tile.getCol() < game.getGameBoard().getColumnSize()) { //check out of bounds
                    System.out.println("worked3");
                    if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.LEFT)) { //check if wall in front, want false
                        System.out.println("worked4");
                        if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { // check if another player marker, want false
                            System.out.println("worked5");
                            return true; //true of there is not wall in front and no player marker
                        }
                    }
                }
            } else { //if there is a second move to move pass the front pawn
                if (direction.get(1) == Tile.Direction.RIGHT) { //another move is up pass pawn
                    if (tile.getCol() < game.getGameBoard().getColumnSize()) { //check out of bounds
                        if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.LEFT)) { //check if wall in front, want false
                            if (!tile.getAdjacent(Tile.Direction.LEFT).canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { // check if another player marker in the first tile (want true)
                                if (tile.getAdjacent(Tile.Direction.LEFT).canAddOnlyBuildings(playerMarker, Tile.Direction.LEFT)) { // Make sure no wall in first front tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { //No player on second tile already, want false
                                        return true; //true if jump spaces twice
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.UP) { //move right diagonal
                    if (tile.getRow() < game.getGameBoard().getRowSize() || tile.getCol() < game.getGameBoard().getColumnSize()) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.DOWN).canAddOnlyEntities(playerMarker, Tile.Direction.RIGHT)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.DOWN).canAddOnlyBuildings(playerMarker, Tile.Direction.RIGHT)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.DOWN)) { //check if wall RIGHT of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.DOWN)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.DOWN) { //move left diagonal
                    if (tile.getRow() >= 0 || tile.getCol() > game.getGameBoard().getColumnSize()) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.UP).canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.UP).canAddOnlyBuildings(playerMarker, Tile.Direction.RIGHT)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.UP)) { //check if wall Left of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.UP)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else if (direction.get(0) == Tile.Direction.LEFT) {
            if (direction.get(1) == Tile.Direction.NONE) { //want to move forward w/ nothing in front and only one move
                if (tile.getCol() >= 0) { //check out of bounds
                    if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.RIGHT)) { //check if wall in front, want false
                        if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.RIGHT)) { // check if another player marker, want false
                            return true; //true of there is not wall in front and no player marker
                        }
                    }
                }
            } else { //if there is a second move to move pass the front pawn
                if (direction.get(1) == Tile.Direction.LEFT) { //another move is up pass pawn
                    if (tile.getCol() >= 0) { //check out of bounds
                        if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.RIGHT)) { //check if wall in front, want false
                            if (!tile.getAdjacent(Tile.Direction.RIGHT).canAddOnlyEntities(playerMarker, Tile.Direction.RIGHT)) { // check if another player marker in the first tile (want true)
                                if (tile.getAdjacent(Tile.Direction.RIGHT).canAddOnlyBuildings(playerMarker, Tile.Direction.RIGHT)) { // Make sure no wall in first front tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.RIGHT)) { //No player on second tile already, want false
                                        return true; //true if jump spaces twice
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.UP) { //move right diagonal
                    if (tile.getRow() < game.getGameBoard().getRowSize() || tile.getCol() >= 0) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.DOWN).canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.DOWN).canAddOnlyBuildings(playerMarker, Tile.Direction.LEFT)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.DOWN)) { //check if wall RIGHT of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.DOWN)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                } else if (direction.get(1) == Tile.Direction.DOWN) { //move left diagonal
                    if (tile.getRow() >= 0 || tile.getCol() <= 0) { //check out of bounds
                        if (!tile.getAdjacent(Tile.Direction.UP).canAddOnlyEntities(playerMarker, Tile.Direction.LEFT)) { //check is player in front first tile, want true
                            if (!tile.getAdjacent(Tile.Direction.UP).canAddOnlyBuildings(playerMarker, Tile.Direction.LEFT)) { //check wall behind first pawn, want true
                                if (tile.canAddOnlyBuildings(playerMarker, Tile.Direction.UP)) { //check if wall Left of Tile, want false
                                    if (tile.canAddOnlyEntities(playerMarker, Tile.Direction.UP)) { // check player marker in tile, want false
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
