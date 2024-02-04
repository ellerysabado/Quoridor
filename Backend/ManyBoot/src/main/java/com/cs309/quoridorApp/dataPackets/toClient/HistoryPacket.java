package com.cs309.quoridorApp.dataPackets.toClient;

import com.cs309.quoridorApp.player.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryPacket extends ClientPacket{

    private double winPercent;

    private String username;

    private List<gameHistory> previousGames = new ArrayList<gameHistory>();

    public HistoryPacket(double winPercent, String username, List<History> previousGames) {
        this.winPercent = winPercent;
        this.username = username;
        this.previousGames = getListHistory(previousGames);
    }

    public List<gameHistory> getListHistory(List<History> playerHistory){
        List<gameHistory> gameHistories = new ArrayList<>();
        for(History history : playerHistory){
            gameHistories.add(new gameHistory(history.getWinner(), history.getPrevOpponents(),history.getMoves()));
        }
        return gameHistories;
    }


    public double getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(double winPercent) {
        this.winPercent = winPercent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<gameHistory> getPreviousGames() {
        return previousGames;
    }

    public void setPreviousGames(List<gameHistory> previousGames) {
        this.previousGames = previousGames;
    }

    public class gameHistory {
        private String winner;

        private List<String> players;

        private List<String> moves;

        public gameHistory(String winner, List<String> players, List<String> moves) {
            this.winner = winner;
            this.players = players;
            this.moves = moves;
        }

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public List<String> getPlayers() {
            return players;
        }

        public void setPlayers(List<String> players) {
            this.players = players;
        }

        public List<String> getMoves() {
            return moves;
        }

        public void setMoves(List<String> moves) {
            this.moves = moves;
        }
    }

    public class previousGames{

        private List<gameHistory> gameHistory= new ArrayList<>();

        public previousGames(List<HistoryPacket.gameHistory> gameHistory) {
            this.gameHistory = gameHistory;
        }

        public List<HistoryPacket.gameHistory> getGameHistory() {
            return gameHistory;
        }

        public void setGameHistory(List<HistoryPacket.gameHistory> gameHistory) {
            this.gameHistory = gameHistory;
        }
    }

}
