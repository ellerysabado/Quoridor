package com.cs309.quoridorApp.dataPackets.toClient;

/**
 * Simple failure packet, returned when given player is not in
 * given game
 */
public class NotInGamePacket extends ClientPacket
{

    public NotInGamePacket()
    {
        setResult(false);
        setMessage("Sessioned Player Not in Selected Game");
    }

}