package com.cs309.quoridorApp.packets;

public class ByteBoardPacket implements IPacket
{
    //private int x;
    //private int y;
    private int[][] bytee;

    public ByteBoardPacket(int[][] bytee)
    {
        //this.x = x;
        //this.y = y;
        this.bytee = bytee;
    }

    public int[][] getBytee() {
        return bytee;
    }

    public void setBytee(int[][] bytee) {
        this.bytee = bytee;
    }

    /*
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
     */
}
