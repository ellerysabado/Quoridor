package com.cs309.quoridorApp.dataPackets.toClient;


public class InvalidSessionPacket extends ClientPacket
{

    public InvalidSessionPacket()
    {
        setResult(false);
        setMessage("Given Session ID is Invalid");
    }

}