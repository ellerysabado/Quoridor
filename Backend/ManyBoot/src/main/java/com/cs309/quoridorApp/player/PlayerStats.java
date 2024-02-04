package com.cs309.quoridorApp.player;

import com.cs309.quoridorApp.controllers.ApiOperation;
import com.cs309.quoridorApp.game.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * This Stats class holds things like win rate, games played, previous opponents,
 * and maybe even allows for replays of games.
 *
 */

@Entity
@Table(name = "PlayerStats")
@RequestMapping("/api")
public class PlayerStats
{
    @Id
    private String username;
    @Column(name = "Wins")
    private int wins;
    @Column(name = "Losses")
    private int losses;
   //@Column(name = "Games Played")
    private int gamesPlayed;
    /**
    @Column(name = "Previous Opponents")
    @ElementCollection
    private List<String> prevOpponents;

     * The replays of the games
     */

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="prevGames")
    private Set<History> prevGames;

    public PlayerStats() {
    }



    public PlayerStats(String username) {
        this.username = username;
        wins = 0;
        losses = 0;
        gamesPlayed = 0;
        //prevOpponents = new ArrayList<>();
        prevGames = new HashSet<>();
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setId(String username) {
        this.username = username;
    }

    public String getId() {
        return username;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /*
    public List<String> getPrevOpponents() {
        return prevOpponents;
    }

    public void setPrevOpponents(List<String> prevOpponents) {
        this.prevOpponents = prevOpponents;
    }

    */

    public Set<History> getPrevGames() {
        //if(prevGames == null)
            //prevGames = new ArrayList<History>();
        return prevGames;
    }

    public void setPrevGames(Set<History> prevGames) {
        this.prevGames = prevGames;
    }

    public List<History> listPrevGames()
    {
        return new ArrayList<>(prevGames);
    }

    public List<String> getPrevOpponents()
    {
        List<String> toReturn = new ArrayList<>();
        for (History games : prevGames)
            for (String opp : games.getPrevOpponents())
                if(!toReturn.contains(opp))
                    toReturn.add(opp);
        return toReturn;
    }

    //TODO: ADD TO AS YOU GET MORE GAME DATA
    @ApiOperation(value = "Update player's stats after a game")

    public void connectGame(Game game)
    {
        setGamesPlayed(gamesPlayed + 1);
    }

    /**
     * This adds the amount of wins to the player
     * while also adding the games played
     * Here, we can also put in the previous opponent and games
     */
    @ApiOperation(value = "Update player's stats after winning a game")

    public void wonGame(){
        wins++;
        gamesPlayed++;
    }

    /**
     * This adds the amount of losses to the player
     * while also adding the games played
     * Here, we can also put in the previous opponent and games
     */
    @ApiOperation(value = "Update player's stats after losing a game")

    public void loseGame(){
        losses++;
        gamesPlayed++;
    }

    /**
     * This function determines the win percentage of the player
     * @return The win rate as a percent
     */
    @ApiOperation(value = "Get player's win rate")
    public double getWinRate() {
        if(losses == 0){
            if(wins > 0){
                return 100;
            } else {
                return 0;
            }
        }

        return (wins / losses) * 100;
    }



}
