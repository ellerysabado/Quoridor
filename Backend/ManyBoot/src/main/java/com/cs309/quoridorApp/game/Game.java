package com.cs309.quoridorApp.game;

import com.cs309.quoridorApp.game.board.Board;
import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.quoridorApp.player.History;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;
import java.util.UUID;

public class Game
{
    /*
    used to hold the game's board, players / spectators, chat, history,
    and whatever else a game needs
     */

    public String id;

    private PlayerList clientList = new PlayerList(this);



    private GameChat chat = new GameChat();

    private GameRules rules = new GameRules(this);
    private GameInfo info;
    private Board gameBoard;

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
        history.updateOpponents(this);
    }

    private History history;


    private int winner = 0;

    public Game(String id) {
        this.id = id;
    }

    public void startGame() {
        gameBoard = new Board(this);
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public GameRules getRules() {
        return rules;
    }

    public PlayerList getClientList() {
        return clientList;
    }

    public int getWinner() {
        return winner;
    }

    public boolean isGameOver(){return winner != 0;}

    public GameChat getChat() {
        return chat;
    }

    public void setChat(GameChat chat) {
        this.chat = chat;
    }

    public void addChat(Player player, String line){
        chat.addChat(player, line);
    }

    /**
     * This is the ending state of the game where it checks to see if a player
     * reached the end of the board and returns the player that won
     * based off of the clientList. For now, it only has two players.
     * I need to change the .getPLayerNumber part
     *
     * This set the game winner
     */
    public void checkEndState() {

        for (Player player : clientList.getPlayers()) {
            // Check if the player has reached the opposite side of the board
            if(clientList.isPlaying(player)) {

                PlayerMarker marker = clientList.getPlayerMaker(player);
                if (marker.getStartTile().getRow() == 0 && marker.getStartTile().getCol() == (gameBoard.getRowSize() / 2)) {
                    if (marker.getTile().getRow() == gameBoard.getRowSize()-1) {
                        winner = clientList.getTeam(player);
                        break;
                        }
                } else if (marker.getStartTile().getRow() == gameBoard.getRowSize()-1 && marker.getStartTile().getCol() == (gameBoard.getRowSize() / 2)) {
                    if (marker.getTile().getRow() == 0) {
                        winner = clientList.getTeam(player);
                        break;
                    }
                } else if (marker.getStartTile().getRow() == (gameBoard.getRowSize() / 2) && marker.getStartTile().getCol() == gameBoard.getColumnSize()-1) {
                    if (marker.getTile().getCol() == 0) {
                        winner = clientList.getTeam(player);
                        break;
                    }
                } else if (marker.getStartTile().getRow() == (gameBoard.getRowSize() / 2) && marker.getStartTile().getCol() == 0) {
                    if(marker.getTile().getCol() == gameBoard.getColumnSize()-1) {
                        winner = clientList.getTeam(player);
                        break;
                    }
                }
            }
        }

        /*if (winner != null) {
            // End the game and declare the winner
            clientList.wonGame(winner);
            history.setWinner(winner.getUsername());
            for (Player player : clientList.getPlayers()) {
                if(player != winner) {
                    clientList.lostGame(player);
                }
            }
        }*/

    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Game && ((Game)o).id.equals(this.id);
    }
    
}
