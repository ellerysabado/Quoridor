package com.cs309.quoridorApp.packets;

public class TestMovePacket
{

    private int x = 0;
    private int y = 0;
    private int d1 = 4;
    private int d2 = 4;

    private String id = "";
    private String gameId = "";

    public TestMovePacket(int x, int y, int direction, int d2, String id, String gameId)
    {
        this.x = x;
        this.y = y;
        this.d1 = direction;
        this.d2 = d2;
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

    public int getD1() {
        return d1;
    }

    public int getD2() {
        return d2;
    }

    public void setD1(int d1) {
        this.d1 = d1;
    }

    public void setD2(int d2) {
        this.d2 = d2;
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
