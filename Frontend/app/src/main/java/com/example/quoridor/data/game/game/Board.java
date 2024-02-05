package com.example.quoridor.data.game.game;

/**
 *
* This class represents a 9x9 quoridor board with walls and regular tiles and can be treated as such.
 * @author Carter Awbrey
* */
public class Board {

    /**
    * The public tiles array that holds all of the tiles for the board.
    * */
    public Tile[][] tiles;


    /**
    * Default constructor for the board.
    * */
    public Board() {
        tiles = new Tile[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                tiles[y][x] = new Tile(x, y);
            }
        }
    }

    /**
    *
     * Places a wall on the board at a specified location and direction.
     *
     * @param x the x position of the tile that is connected to the wall
     * @param y the y position of the tile connected to the wall
     * @param direction the direction of the wall 0=horizontal 1=vertical
     *
     * @return whether or not the wall can be placed at the specified position
    * */
    public boolean placeWall(int x, int y, int direction){
        if(x > 7 || y > 7 ||
                (direction == 0 && (tiles[y][x].bottomWall || (tiles[y][x].rightWall && tiles[y][x + 1].rightWall)) ||
                (direction == 1 && (tiles[y][x].rightWall || (tiles[y][x].bottomWall && tiles[y][x + 1].bottomWall) )))){
            return false;
        }

        if(direction == 0){
            //horizontal
            tiles[y][x].bottomWall = true;
            tiles[y][x + 1].bottomWall = true;
            tiles[y + 1][x].topWall = true;
            tiles[y + 1][x + 1].topWall = true;
        }else if(direction == 1){
            //vertical
            tiles[y][x].rightWall = true;
            tiles[y][x + 1].leftWall = true;
            tiles[y + 1][x].rightWall = true;
            tiles[y + 1][x + 1].leftWall = true;
        }

        return true;
    }

    /**
     *
     * Removes a wall on the board at a specified location and direction.
     *
     * @param x the x position of the tile that is connected to the wall
     * @param y the y position of the tile connected to the wall
     * @param direction the direction of the wall 0=horizontal 1=vertical
     * */
    public void removeWall(int x, int y, int direction){
        if(direction == 0){
            //horizontal
            tiles[y][x].bottomWall = false;
            tiles[y][x + 1].bottomWall = false;
            tiles[y + 1][x].topWall = false;
            tiles[y + 1][x + 1].topWall = false;
        }else if(direction == 1){
            //vertical
            tiles[y][x].rightWall = false;
            tiles[y][x + 1].leftWall = false;
            tiles[y + 1][x].rightWall = false;
            tiles[y + 1][x + 1].leftWall = false;
        }
    }

    /**
     *
     * Moves character on board.
     *
     * @param startX the starting x position of the character
     * @param startY the starting y position of the character
     * @param endX the destination position of the character
     * @param endY the destination position of the character
     *
     * @return whether or not the player can be move there
     *
     * @apiNote No actual game checking happens here, use with caution. It only checks whether the destination square is free.
     * */
    public boolean moveCharacter(int startX, int startY, int endX, int endY){
        if(tiles[endY][endX].player != -1 || tiles[startY][startX].player == -1){
            return false;
        }

        tiles[endY][endX].player = tiles[startY][startX].player;

        tiles[startY][startX].player = -1;

        return true;
    }
}
