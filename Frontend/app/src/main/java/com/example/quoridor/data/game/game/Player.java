package com.example.quoridor.data.game.game;


/**
 *
* Object that represents a player for the main game.
 * @author Carter Awbrey
* */
public class Player {
    public String name;
    public int number;
    public String role;
    public Integer numWalls = 10;
    public Integer startX;
    public Integer startY;
    public Integer x;
    public Integer y;


    /**
    *
     * This constructor is used for the lobby
     *
     * @param name username of the player(or internal name if desired)
     * @param role role of the player(could be "admin" or "client" or "host")
    * */
    public Player(String name, String role){
        this.name = name;
        this.role = role;
    }

    /**
     * This constructor is used for the main game
     *
     * @param name username of the player(or internal name if desired)
     * @param role role of the player(could be "admin" or "client" or "host")
     * */
    public Player(String name, String role, int number, int startX, int startY){
        this.name = name;
        this.role = role;
        this.number = number;
        this.startX = startX;
        this.startY = startY;
    }

    public Player(String name, String role, int numWalls, int number, int startX, int startY){
        this.name = name;
        this.role = role;
        this.number = number;
        this.startX = startX;
        this.startY = startY;
        this.numWalls = numWalls;
    }

    public Player(){}
}
