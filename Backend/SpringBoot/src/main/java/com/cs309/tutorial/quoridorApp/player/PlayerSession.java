package com.cs309.tutorial.quoridorApp.player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class PlayerSession
{
    @Id
    private UUID uuid;

    @OneToOne
    private Player player;

    //maybe add more data

    public PlayerSession(Player player)
    {
        this.player = player;
        this.uuid = UUID.randomUUID();
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
    }
}
