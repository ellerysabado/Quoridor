package com.cs309.quoridorApp.player;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Generated;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.UUID;

@Entity
@ApiModel(description = "Represents a session for a player.")
@RequestMapping("/api")

public class PlayerSession
{
    @Id
    @ApiModelProperty(notes = "The unique identifier for the player session.")
    private String id;

    @OneToOne
    @ApiModelProperty(notes = "The player associated with this session.")
    private Player player;

    //maybe add more data

    public PlayerSession(Player player)
    {
        this.player = player;
        id = UUID.randomUUID().toString();
    }

    public PlayerSession() {}

    public void setId(String uuid) {
        this.id = uuid;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }
}
