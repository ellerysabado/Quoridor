package com.cs309.quoridorApp.packets;

import java.util.UUID;

public class SessionConfirmationPacket implements IPacket
{
    private String id;
    private String error;

    public SessionConfirmationPacket(String id, String error)
    {
        this.id = id;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getId() {
        return id;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setId(String id) {
        this.id = id;
    }
}
