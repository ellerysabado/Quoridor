package com.cs309.quoridorApp.dataPackets.toClient;

/**
 * Simple failure packet, returned when SuperPacket's function
 * does not correlate to a packet class type
 */
public class PacketNotFoundPacket extends ClientPacket
{

    public PacketNotFoundPacket()
    {
        setResult(false);
        setMessage("Given Packet Type Does Not Exist");
    }

}
