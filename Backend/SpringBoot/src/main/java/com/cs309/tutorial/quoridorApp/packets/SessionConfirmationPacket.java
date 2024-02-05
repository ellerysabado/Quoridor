package com.cs309.tutorial.quoridorApp.packets;

import java.util.UUID;

public class SessionConfirmationPacket implements IPacket
{
    private UUID uuid;
    private String error;

    public SessionConfirmationPacket(UUID uuid, String error)
    {
        this.uuid = uuid;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
