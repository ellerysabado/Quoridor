package com.example.quoridor.data.game.game;


/**
* Object that represents a tile object for the game board.
 * @author Carter Awbrey
* */
public class Tile {

    public int x;
    public int y;

    public boolean topWall = false;
    public boolean rightWall = false;
    public boolean bottomWall = false;
    public boolean leftWall = false;
    public int player = -1;


    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Tile(){}
}
