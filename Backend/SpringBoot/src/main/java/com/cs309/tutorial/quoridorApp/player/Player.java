package com.cs309.tutorial.quoridorApp.player;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @Column(name = "Username", nullable = false, length = 100)
    private String username;

    @Column(name = "Password", nullable = false, unique = false)
    private String password;

    @Column(name = "Name", nullable = false, unique = false)
    private String name;

    @ManyToMany
    @JsonIgnore
    private List<Player> friends;
    @ManyToMany
    @JsonIgnore
    private List<Player> friendRequests;
    @ManyToMany
    @JsonIgnore
    private List<Player> blockedPlayers;

    @JsonIgnore
    private PlayerStats playerStats;

    public Player(String name, String username, String password)
    {
        this.name = name;
        this.username = username;
        this.password = password;
        this.playerStats = new PlayerStats();
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.blockedPlayers = new ArrayList<>();
    }

    public Player() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    public List<Player> getFriends() {
        return friends;
    }

    public void setFriends(List<Player> friends) {
        this.friends = friends;
    }

    public List<Player> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<Player> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public List<Player> getBlockedPlayers() {
        return blockedPlayers;
    }

    public void setBlockedPlayers(List<Player> blockedPlayers) {
        this.blockedPlayers = blockedPlayers;
    }
}
