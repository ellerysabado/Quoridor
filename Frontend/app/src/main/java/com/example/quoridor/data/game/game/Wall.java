package com.example.quoridor.data.game.game;

/**
 * Object that represents a Wall object for the game board.
 * @author Carter Awbrey
 * */
public class Wall {
    public int x;
    public int y;
    public int dir;

    public Wall(int x, int y, int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Wall(){}
}
