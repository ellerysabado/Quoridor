package com.cs309.quoridorApp.player;

import com.cs309.quoridorApp.game.board.Tile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a player of the Quoridor game. It contains information about the player such as
 * their username, password, friend list, blocked players list, player statistics and more.
 * It is annotated with JPA annotations to be mapped to a database table named "player".
 */
@Entity
@Table(name = "player")
@RequestMapping("/api")
@ApiModel(description = "Details about the player")
public class Player {

    /**
     * The player's username, which is also the primary key in the "player" table
     */
    @Id
    @Column(name = "Username", nullable = false, length = 100)
    @ApiModelProperty(notes = "The player's username")
    private String username;

    /**
     * The player's password
     */
    @Column(name = "Password", nullable = false, unique = false)
    @ApiModelProperty(notes = "The player's password")
    private String password;

    /**
     * A list of the player's friends
     */
    @ManyToMany
    @JsonIgnore
    private List<Player> friends;

    /**
     * A list of the player's friends
     */
    @ManyToMany
    @JsonIgnore
    private List<Player> friendRequests;

    /**
     * A list of friend requests that the player has sent
     */
    @ManyToMany
    @JsonIgnore
    private List<Player> requestedFriends;

    /**
     * A list of players that the player has blocked
     */
    @ManyToMany
    @JsonIgnore
    private List<Player> blockedPlayers;


    @ManyToMany
    @JsonIgnore
    private List<Player> blockedBy;

    @OneToOne
    @JsonIgnore
    private PlayerStats playerStats;

    //@OneToMany(fetch = FetchType.EAGER)
    //private List<History> previousGames;

    public Player(String username, String password)
    {
        //this.name = name;
        this.username = username;
        this.password = password;
        this.playerStats = new PlayerStats(username);
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.blockedPlayers = new ArrayList<>();
        //this.previousGames = new ArrayList<>();
    }

    public Player()
    {}

    @ApiModelProperty(notes = "The player's username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @ApiModelProperty(notes = "The player's password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     */

    @ApiModelProperty(notes = "The player's stats")
    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    @ApiModelProperty(notes = "The player's friends")
    public List<Player> getFriends() {
        return friends;
    }

    public void setFriends(List<Player> friends) {
        this.friends = friends;
    }
    @ApiModelProperty(notes = "The player's friend requests")
    public List<Player> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<Player> friendRequests) {
        this.friendRequests = friendRequests;
    }
    @ApiModelProperty(notes = "The player's requested friends")
    public List<Player> getRequestedFriends() {
        return requestedFriends;
    }

    public void setRequestedFriends(List<Player> requestedFriends) {
        this.requestedFriends = requestedFriends;
    }
    @ApiModelProperty(notes = "The player's blocked players")
    public List<Player> getBlockedPlayers() {
        return blockedPlayers;
    }

    public void setBlockedPlayers(List<Player> blockedPlayers) {
        this.blockedPlayers = blockedPlayers;
    }

    public List<Player> getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(List<Player> blockedBy) {
        this.blockedBy = blockedBy;
    }

    /*
    public List<History> getPreviousGames() {
        return previousGames;
    }

    public void setPreviousGames(List<History> previousGames) {
        this.previousGames = previousGames;
    }

     */

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Player && ((Player)o).getUsername().equals(username);
    }
}

