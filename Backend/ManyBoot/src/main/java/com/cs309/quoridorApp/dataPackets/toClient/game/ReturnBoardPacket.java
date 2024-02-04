package com.cs309.quoridorApp.dataPackets.toClient.game;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.game.Game;
import com.cs309.quoridorApp.game.board.tileBound.DoubleWall;
import com.cs309.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns the current player, winner (if someone has won),
 * and positions of players and walls.
 *
 * Accepts entire game as parameter.
 */
public class ReturnBoardPacket extends ClientPacket
{

    private String winner = "";
    private int currentPlayer = 0;
    private List<ClientMarkers> pieces = new ArrayList<>();
    private List<ClientWalls> walls = new ArrayList<>();

    public ReturnBoardPacket(Game game)
    {
        PlayerList pl = game.getClientList();
        this.winner = game.getWinner() == 0 ? "" : pl.getTeamMembers(game.getWinner()).get(0).getUsername();
        this.currentPlayer = pl.getCurrentTurn();

        for(Player player : pl.getOrderedPlayers())
        {
            PlayerMarker pm = pl.getPlayerMaker(player);
            this.pieces.add(new ClientMarkers(pm.getX(), pm.getY(), pl.getWallsRemaining(player)));
            for(DoubleWall wall : pl.getWalls(player))
                this.walls.add(new ClientWalls(wall));
        }

    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public List<ClientMarkers> getPieces() {
        return pieces;
    }

    public String getWinner() {
        return winner;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPieces(List<ClientMarkers> pieces) {
        this.pieces = pieces;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public class ClientMarkers
    {
        private int x;
        private int y;
        private int wallsRemaining;

        public ClientMarkers(int x, int y, int wallsRemaining)
        {
            this.x = x;
            this.y = y;
            this.wallsRemaining = wallsRemaining;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWallsRemaining() {
            return wallsRemaining;
        }

        public void setWallsRemaining(int wallsRemaining) {
            this.wallsRemaining = wallsRemaining;
        }
    }

    public class ClientWalls
    {
        private int x;
        private int y;
        private int direction;

        public ClientWalls(int x, int y, int direction)
        {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public ClientWalls(DoubleWall wall)
        {
            this.x = wall.getTile().x;
            this.y = wall.getTile().y;
            this.direction = wall.getDirectionOfWall().equals("Horizontal") ? 0 : 1;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }
    }
}
