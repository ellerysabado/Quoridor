package com.example.quoridor.data.game.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Quoridor game engine.
 * @author Carter Awbrey
 * */
public class QuoridorEngine {
    private final int numPlayers;

    //Current Player's Turn 0-3
    public int currentPlayer;
    public final Player[] players;

    //0 - gameRunning
    //1 - Player 1 Won
    //2 - Player 2 Won
    //3 - Player 3 Won
    //4 - Player 4 Won
    public int gameState;

    public final Board board;

    public QuoridorEngine(Player[] players, int StartingPlayer){
        this.numPlayers = players.length;
        this.players = players;
        gameState = 0;
        currentPlayer = StartingPlayer;
        board = new Board();

        if (numPlayers != 2 && numPlayers != 4) {
            throw new RuntimeException("Unexpected Number Of Players, Expected 2 or 4");
        }

        for (Player i: this.players) {
            if(i.x == null || i.y == null){
                i.y = i.startY;
                i.x = i.startX;
            }
            board.tiles[i.y][i.x].player = i.number;
        }
    }

    private void Update_Game_State(){
        for (Player i: players) {
            if(     (i.startX == 0 && i.x == 8) ||
                    (i.startX == 8 && i.x == 0) ||
                    (i.startY == 0 && i.y == 8) ||
                    (i.startY == 8 && i.y == 0) ){
                gameState = i.number;
                return;
            }
        }
    }

    private void Cycle_Current_Player(){
        currentPlayer = (currentPlayer + 1)%numPlayers;
    }

    public boolean Move_Player(int playerNum, int x, int y){
        Update_Game_State();
        if(!Is_Valid_Move(playerNum, x, y)){
            return false;
        }

        if(board.moveCharacter(players[playerNum].x, players[playerNum].y, x, y)){
            players[playerNum].x = x;
            players[playerNum].y = y;
            Cycle_Current_Player();
            return true;
        }else{
            return false;
        }
    }

    public boolean Place_Wall(int playerNum, int x, int y, int dir){
        Update_Game_State();
        if(playerNum != currentPlayer ||
                x < 0 ||
                y < 0 ||
                x > 9 ||
                y > 9 ||
                (dir != 0 && dir != 1)||
                gameState != 0 ||
                players[playerNum].numWalls == 0){
            return false;
        }

        if(!board.placeWall(x, y, dir)){
            return false;
        }

        if(!Players_Can_Win()){
            board.removeWall(x, y, dir);
            return false;
        }


        Cycle_Current_Player();
        return true;
    }

    public List<Tile> Get_Available_Moves(int playerNum){
        List<Tile> moves = new ArrayList<>();
        if(gameState != 0 || currentPlayer != playerNum){
            return moves;
        }

        int x = players[playerNum].x;
        int y = players[playerNum].y;

        return Get_Available_Moves(x, y);
    }

    private List<Tile> Get_Available_Moves(int x, int y){
        List<Tile> moves = new ArrayList<>();
        Tile cT = board.tiles[y][x];

        if(y-1 >= 0 && !cT.topWall){
            //north movement(-y)
            if (board.tiles[y - 1][x].player != -1) {
                //player to north

                if(!board.tiles[y - 1][x].topWall && y-2 >= 0 && board.tiles[y - 2][x].player == -1){
                    //double jump straight
                    moves.add(board.tiles[y - 2][x]);
                }

                if(board.tiles[y - 1][x].topWall){
                    //double jump diagonal
                    if(x+1 <= 8 && !board.tiles[y - 1][x].rightWall){
                        //right(+x)
                        moves.add(board.tiles[y - 1][x + 1]);
                    }

                    if(x-1 >= 0 && !board.tiles[y - 1][x].leftWall){
                        //left(-x)
                        moves.add(board.tiles[y - 1][x - 1]);
                    }
                }

            } else {
                //no player to north
                moves.add(board.tiles[y-1][x]);
            }
        }

        if(y+1 <= 8 && !cT.bottomWall){
            //south movement(+y)
            if (board.tiles[y + 1][x].player != -1) {
                //player to south

                if(!board.tiles[y + 1][x].bottomWall && y+2 <= 8 && board.tiles[y + 2][x].player == -1){
                    //double jump straight
                    moves.add(board.tiles[y + 2][x]);
                }

                if(board.tiles[y + 1][x].bottomWall){
                    //double jump diagonal
                    if(x+1 <= 8 && !board.tiles[y + 1][x].rightWall){
                        //right(+x)
                        moves.add(board.tiles[y + 1][x + 1]);
                    }

                    if(x-1 >= 0 && !board.tiles[y + 1][x].leftWall){
                        //left(-x)
                        moves.add(board.tiles[y + 1][x - 1]);
                    }
                }

            } else {
                //no player to south
                moves.add(board.tiles[y+1][x]);
            }
        }

        if(x-1 >= 0 && !cT.leftWall){
            //west movement(-x)
            if (board.tiles[y][x - 1].player != -1) {
                //player to west

                if(!board.tiles[y][x - 1].leftWall && x-2 >= 0 && board.tiles[y][x - 2].player == -1){
                    //double jump straight
                    moves.add(board.tiles[y][x - 2]);
                }

                if(board.tiles[y][x - 1].leftWall){
                    //double jump diagonal
                    if(y+1 <= 8 && !board.tiles[y][x - 1].topWall){
                        //up(+y)
                        moves.add(board.tiles[y + 1][x - 1]);
                    }

                    if(y-1 >= 0 && !board.tiles[y][x - 1].bottomWall){
                        //down(-y)
                        moves.add(board.tiles[y - 1][x - 1]);
                    }
                }

            } else {
                //no player to west
                moves.add(board.tiles[y][x - 1]);
            }
        }

        if(x+1 <= 8 && !cT.rightWall){
            //west movement(-x)
            if (board.tiles[y][x + 1].player != -1) {
                //player to east

                if(!board.tiles[y][x + 1].rightWall && x+2 <= 8 && board.tiles[y][x + 2].player == -1){
                    //double jump straight
                    moves.add(board.tiles[y][x + 2]);
                }

                if(board.tiles[y][x + 1].rightWall){
                    //double jump diagonal
                    if(y+1 <= 8 && !board.tiles[y][x + 1].topWall){
                        //up(+y)
                        moves.add(board.tiles[y + 1][x + 1]);
                    }

                    if(y-1 >= 0 && !board.tiles[y][x + 1].bottomWall){
                        //down(-y)
                        moves.add(board.tiles[y - 1][x + 1]);
                    }
                }

            } else {
                //no player to east
                moves.add(board.tiles[y][x + 1]);
            }
        }

        return moves;
    }

    private boolean Is_Valid_Move(int playerNum, int x, int y){
        List<Tile> validTiles = Get_Available_Moves(playerNum);

        if(gameState == 0){
            for (Tile i : validTiles) {
                if(i.x == x && i.y == y){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean Players_Can_Win(){
        for (Player i : players) {
            if(!Player_Can_Win(i.number)){
                return false;
            }
        }

        return true;
    }

    private boolean Player_Can_Win(int playerNum){
        boolean[][] visited = new boolean[9][9];
        for (boolean[] i: visited) {
            for(boolean j: i){
                j = false;
            }
        }
        Queue<pair> q = new LinkedList<>();

        q.add(new pair(players[playerNum].x, players[playerNum].y));
        visited[players[playerNum].y][players[playerNum].x] = true;

        while(!q.isEmpty()){

            pair cur = q.peek();
            List<Tile> nextMoves = Get_Available_Moves(cur.x, cur.y);
            q.remove();
            for (Tile i : nextMoves) {
                if(!visited[i.y][i.x]){
                    if(     (players[playerNum].startX == 0 && i.x == 8)||
                            (players[playerNum].startX == 8 && i.x == 0)||
                            (players[playerNum].startY == 0 && i.y == 8)||
                            (players[playerNum].startY == 8 && i.y == 0)){
                        return true;
                    }

                    q.add(new pair(i.x, i.y));
                    visited[i.y][i.x] = true;
                }
            }
        }

        return false;
    }

    public List<Player> Get_Players_List(){
        return Arrays.asList(players);
    }

    public List<Wall> Get_Walls_ArrayList(){
        List<Wall> walls = new ArrayList<>();

        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                if(board.tiles[y][x].bottomWall){
                    walls.add(new Wall(x, y, 0));
                    x++;
                }
            }
        }

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(board.tiles[y][x].rightWall){
                    walls.add(new Wall(x, y, 1));
                    y++;
                }
            }
        }

        return walls;
    }

    public int Get_Game_State(){
        Update_Game_State();
        return gameState;
    }

    public int Get_Current_Player(){
        return currentPlayer;
    }

    static class pair
    {
        int x, y;

        public pair(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}


