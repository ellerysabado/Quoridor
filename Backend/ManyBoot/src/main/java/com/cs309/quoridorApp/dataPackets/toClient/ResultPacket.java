package com.cs309.quoridorApp.dataPackets.toClient;

/**
 * Simple packet used to return whether a packet failed or succeeded
 */
public class ResultPacket extends ClientPacket
{
    public ResultPacket(boolean result, String message)
    {
        setResult(result);
        setMessage(message);
    }
}
