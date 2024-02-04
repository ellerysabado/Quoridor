package com.cs309.quoridorApp.packets;

public class ResultPacket implements IPacket
{

    private boolean result;
    private String reason;

    public ResultPacket(boolean result, String reason)
    {
        this.result = result;
        this.reason = reason;
    }

    public boolean isResult() {
        return result;
    }

    public String getReason() {
        return reason;
    }
}
