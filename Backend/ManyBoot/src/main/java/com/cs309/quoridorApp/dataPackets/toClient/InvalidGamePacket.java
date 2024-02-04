package com.cs309.quoridorApp.dataPackets.toClient;

/**
 * Simple failure packet, returned when given game does not exist
 */
public class InvalidGamePacket extends ClientPacket
{

    public InvalidGamePacket()
    {
        setResult(false);
        setMessage("Given Game Does Not Exist");
    }

}
