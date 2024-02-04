package com.cs309.quoridorApp.player;
import com.cs309.quoridorApp.game.Game;
import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.game.board.tileBound.DoubleWall;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Generated;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



/**
 * In this class it will use the username as the ID and have the previous opponents played,
 * the prev game and the moves. I haven't decided If i want to keep the moves list because
 * it can be apart of the prev games list. But for the moves, it can stor the which opponent
 * make the move, the location of the move and what was placed (e.i. pawn or wall)
 *
 *
 *
 * I plan of creating making the previousGame a 2D arraylist or some hash map to store moves in each PrevGame
 */
@Entity
@RequestMapping("/api")
@ApiModel(description = "Stores the game history for a player.")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The ID of the game history entry.")
    private int gameID;

    @Column(name = "PreviousOpponent")
    @ElementCollection
    @ApiModelProperty(notes = "The list of usernames of previous opponents.")
    private List<String> prevOpponents;

    @Column(name = "Moves")
    @ElementCollection
    @ApiModelProperty(notes = "The list of moves made in the game.")
    private List<String> moves;

    @Column(name = "Winner")
    @ApiModelProperty(notes = "The username of the winner of the game.")
    private String winner;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="prevGames")
    @JsonIgnore
    private List<PlayerStats> stats;

    public History() {
        prevOpponents = new ArrayList<String>();
        moves = new ArrayList<>();
        stats = new ArrayList<>();
        winner = "";
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public List<String> getPrevOpponents() {
        return prevOpponents;
    }

    public void setPrevOpponents(List<String> prevOpponents) {
        this.prevOpponents = prevOpponents;
    }

    public void updateOpponents(Game game)
    {
            for(Player player : game.getClientList().getClients())
                prevOpponents.add(player.getUsername());
    }

    public List<PlayerStats> getStats() {
        return stats;
    }

    public void setStats(List<PlayerStats> stats) {
        this.stats = stats;
    }

    public List<String> getMoves() {
        return moves;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @ApiModelProperty(notes = "Adds a move made by a player with a pawn.")
    public void addPlayerMove(Player player, Tile tile){
        String move = "" + player.getUsername() + "\tRow: " + tile.getRow() + "   Column: \n" + tile.getCol();
        moves.add(move);
    }

    @ApiModelProperty(notes = "Adds a move made by a player with a wall.")
    public void addWallMove(Player player, DoubleWall doubleWall){
        String move = "" + player.getUsername() + "\tRow: " + doubleWall.getTile().getRow() + "   Column: " + doubleWall.getTile().getRow() + " " + doubleWall.getDirectionOfWall() + "\n";
        moves.add(move);
    }
}
