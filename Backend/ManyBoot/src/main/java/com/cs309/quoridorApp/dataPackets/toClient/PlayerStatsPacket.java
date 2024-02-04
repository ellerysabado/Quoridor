package com.cs309.quoridorApp.dataPackets.toClient;

public class PlayerStatsPacket extends ClientPacket{

    private String username;

    private int wins;

    private int losses;

    public PlayerStatsPacket(String username, int win, int lost) {
        this.username = username;
        this.wins = win;
        this.losses = lost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWin() {
        return wins;
    }

    public void setWin(int win) {
        this.wins = win;
    }

    public int getLost() {
        return losses;
    }

    public void setLost(int lost) {
        this.losses = lost;
    }
}
