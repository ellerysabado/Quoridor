package com.cs309.quoridorApp.packets;

public class TestWallPacket {

    private int x = 0;
    private int y = 0;
    private int isHorizontal = 0;

    private String id = "";
    private String gameId = "";

    public TestWallPacket(int x, int y, int isHorizontal, String id, String gameId)
    {
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
        this.id = id;
        this.gameId = gameId;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getIsHorizontal() {
        return isHorizontal;
    }

    public void setIsHorizontal(int isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

}
