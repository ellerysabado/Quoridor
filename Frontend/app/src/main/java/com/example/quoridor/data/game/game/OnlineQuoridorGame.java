package com.example.quoridor.data.game.game;

import com.example.quoridor.data.constants;
import com.example.quoridor.utils.LoadingController;
import com.example.quoridor.utils.net.RequestListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;


/**
 *
* Controller for a network-enabled quoridor game.
 * @author Carter Awbrey
* */
public class OnlineQuoridorGame {
    public QuoridorEngine game;

    public String username;
    public String uuid;
    public String session;
    public LoadingController loadCont;
    public JSONObject settings;

    private boolean ignoreMove = false;

    private boolean initialized = false;

    public WebSocketClient ws;

    public int myPlayer;

    /**
     * Default Constructor
     * @param session the session id of the desired game
     * @param loadCont interface that starts and stops a loading symbol for when data is being fetched
     * @param username username for the local user of the game
     * @param uuid uuid of the local user
    * */
    public OnlineQuoridorGame(String uuid, String username, String session, LoadingController loadCont ){
        this.username = username;
        this.uuid = uuid;
        this.session = session;
        this.loadCont = loadCont;
        this.myPlayer = 0;

        init();
    }

    /**
     * Initializes the board and players array of the game by fetching data from the server.
    * */
    private void init(){
        //retrieving settings from server

        Draft[] drafts = { new Draft_6455() };

        try{
            ws = new WebSocketClient(new URI(constants.websocket_url + "game/" + uuid), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    initialized = true;
                    System.out.println("Websocket Opened for Main Game.");
                }

                @Override
                public void onMessage(String s) {
                    try {
                        JSONObject response = new JSONObject(s);

                        if (response.getString("function").equals("PLAYERMOVED")) {
                            int player = response.getJSONObject("player").getInt("teamNumber") - 1;

                            if(!game.Move_Player(player, response.getInt("x"), response.getInt("y"))){
                                throw new RuntimeException("Server and client disagree with player movement.");
                            }

                        }else if(response.getString("function").equals("WALLPLACED")){
                            int player = response.getJSONObject("player").getInt("teamNumber") - 1;
                            if(!game.Place_Wall(player,
                                    response.getInt("x"),
                                    response.getInt("y"),
                                    response.getInt("direction"))){
                                throw new RuntimeException("Server and client disagree with wall placement.");
                            }

                        } else if (response.getString("function").equals("NEWTURN")) {

                            if(response.isNull("player")){
                                game.gameState = game.currentPlayer;

                            }else if(game.Get_Game_State() == 0){
                                int player = response.getJSONObject("player").getInt("teamNumber") - 1;
                                if(player != game.currentPlayer){
                                    throw new RuntimeException("Server and client disagreed on current player.");
                                }
                                game.currentPlayer = player;
                            }

                            loadCont.FinishLoading();
                        }

                        loadCont.FinishLoading();
                    } catch (JSONException e) {
                        System.out.println("Unknown message from server: " + s);
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("----------Websocket closed----------");
                    //initialized = false;
                    if(game.gameState == 0){
                        throw new RuntimeException("Connection closed unexpectedly!");
                    }
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("Websocket encountered error: " + e.toString());
                    //throw new RuntimeException("Unable to connect to websocket!");
                }
            };
        }catch (Exception e){
            throw new RuntimeException("Unknown problem creating websocket.");
        }

        GameNet.GetSettings(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                JSONObject r = ((JSONObject) response);

                try{
                    if(r.getString("message").isEmpty()){
                        settings = r;
                        settings.remove("message");

                        Player[] tempPlayers = new Player[settings.getInt("player_count")];


                        tempPlayers[0] = new Player();
                        tempPlayers[0].startX = 0;
                        tempPlayers[0].startY = 4;
                        tempPlayers[0].numWalls = 10;
                        tempPlayers[0].number = 0;

                        tempPlayers[1] = new Player();
                        tempPlayers[1].startX = 8;
                        tempPlayers[1].startY = 4;
                        tempPlayers[1].numWalls = 10;
                        tempPlayers[1].number = 1;

                        if(tempPlayers.length == 4){
                            tempPlayers[0].numWalls = 5;
                            tempPlayers[1].numWalls = 5;

                            tempPlayers[2] = new Player();
                            tempPlayers[2].startX = 4;
                            tempPlayers[2].startY = 8;
                            tempPlayers[2].numWalls = 5;
                            tempPlayers[2].number = 2;

                            tempPlayers[3] = new Player();
                            tempPlayers[3].startX = 4;
                            tempPlayers[3].startY = 0;
                            tempPlayers[3].numWalls = 5;
                            tempPlayers[3].number = 3;
                        }

                        game = new QuoridorEngine(tempPlayers, 0);
                    }else{
                        System.out.println(r.getString("message"));
                    }
                }catch (JSONException e){
                    throw new RuntimeException("Unable to parse server board JSON object.");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println("Unable to connect to server!");
                throw new RuntimeException(errorMessage);
            }
        });

        GameNet.GetBoard(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                try{
                    JSONObject r = ((JSONObject) response);
                    if(r.getString("message").isEmpty()){
                        //creating players array
                        JSONArray piecesServ = r.getJSONArray("pieces");
                        Player[] players = game.players;

                        for(int i = 0; i < players.length; i++){
                            players[i].startX = piecesServ.getJSONObject(i).getInt("x");
                            players[i].x = players[i].startX;
                            players[i].startY = piecesServ.getJSONObject(i).getInt("y");
                            players[i].y = players[i].startY;
                            players[i].numWalls = piecesServ.getJSONObject(i).getInt("wallsRemaining");
                        }

                        game.currentPlayer = r.getInt("currentPlayer");

                    }else{
                        System.out.println(r.getString("message"));
                    }
                }catch (JSONException e){
                    throw new RuntimeException("Unable to parse server board JSON object.");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println("Unable to connect to server!");
                throw new RuntimeException(errorMessage);
            }
        });

        GameNet.GetPlayers(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                try{
                    JSONObject r = ((JSONObject) response);
                    if(r.getString("message").isEmpty()){
                        //creating players array
                        JSONArray playersServ = r.getJSONArray("players");
                        Player[] players = game.players;

                        for(int i = 0; i < players.length; i++){
                            players[i].name = playersServ.getJSONObject(i).getString("name");
                            if(players[i].name.equals(username)){
                                myPlayer = playersServ.getJSONObject(i).getInt("teamNumber") - 1;

                                if(myPlayer != players[i].number){
                                    throw new RuntimeException("Shit data received from server!");
                                }
                            }
                            players[i].role = playersServ.getJSONObject(i).getString("role");
                            players[i].number = playersServ.getJSONObject(i).getInt("teamNumber") - 1;
                        }

                        ws.connect();
                        loadCont.FinishLoading();

                    }else{
                        System.out.println(r.getString("message"));
                    }
                }catch (JSONException e){
                    throw new RuntimeException("Unable to parse server board JSON object.");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println("Unable to connect to server!");
                throw new RuntimeException(errorMessage);
            }
        });

    }


    /**
    * Moves the client player with the backend.
     *
     * @param x x destination of player
     * @param y y destination of player
    * */
    public boolean Move_Player(int x, int y){
        int x1 = game.players[myPlayer].x;
        int y1 = game.players[myPlayer].y;
        int x2 = x, y2 = y;

        if(initialized && game.Get_Game_State() == 0 && game.Move_Player(myPlayer, x, y)){

            //determine if the move was a double jump
            if(Math.abs((x1-x2) + (y1-y2)) > 1){
                if(x1 != x2 && y1 != y2){
                    //moved diagonally
                    if(y1 + 1 < 9 && game.board.tiles[y1 + 1][x1].player != -1){
                        //up (+y)
                        y1++;
                    }else if(y1 - 1 >= 0 && game.board.tiles[y1 - 1][x1].player != -1){
                        //down (-y)
                        y1--;
                    }else if(x1 + 1 < 9 && game.board.tiles[y1][x1 + 1].player != -1){
                        //right(+x)
                        x1++;
                    }else if(x1 - 1 >= 0 && game.board.tiles[y1][x1 - 1].player != -1){
                        //left(-x)
                        x1--;
                    }else{
                        //error!
                        throw new RuntimeException("Unable to find diagonal jump move made!");
                    }
                }else{
                    //moved straight
                    if((y2 - y1) == 2){
                        //up (+y)
                        y1++;
                    }else if((y1 - y2) == 2){
                        //down (-y)
                        y1--;
                    }else if((x2 - x1) == 2){
                        //right(+x)
                        x1++;
                    }else if((x1 - x2) == 2){
                        //left(-x)
                        x1--;
                    }else{
                        //error!
                        throw new RuntimeException("Unable to find diagonal jump move made!");
                    }
                }


                GameNet.MovePlayer(uuid, session, x1, y1, x2, y2, new RequestListener() {
                    @Override
                    public void onSuccess(Object response) {
                        JSONObject r = ((JSONObject) response);
                        try{
                            if(!r.getString("message").isEmpty()){
                                System.out.println(r.getString("message"));
                                throw new RuntimeException("Server and client disagree with double Move_Player.");
                            }else{
                                loadCont.FinishLoading();
                            }
                        }catch (JSONException e){
                            throw new RuntimeException("Unable to parse server board JSON object.");
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        System.out.println("Unable to connect to server!");
                        throw new RuntimeException(errorMessage);
                    }
                });

            }else{
                GameNet.MovePlayer(uuid, session, x, y, new RequestListener() {
                    @Override
                    public void onSuccess(Object response) {
                        JSONObject r = ((JSONObject) response);
                        try{
                            if(!r.getString("message").isEmpty()){
                                System.out.println(r.getString("message"));
                                throw new RuntimeException("Server and client disagree with Move_Player.");
                            }else{
                                loadCont.FinishLoading();
                            }
                        }catch (JSONException e){
                            throw new RuntimeException("Unable to parse server board JSON object.");
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        System.out.println("Unable to connect to server!");
                        throw new RuntimeException(errorMessage);
                    }
                });
            }
            return true;
        }
        return false;
    }

    /**
     * Places a wall for the client player
     *
     * @param x x destination of wall
     * @param y y destination of wall
     * @param dir direction of the wall
     * */
    public boolean Place_Wall(int x, int y, int dir){
        if(initialized && game.Get_Game_State() == 0 && game.Place_Wall(myPlayer, x, y, dir)){
            GameNet.PlaceWall(uuid, session, x, y, dir, new RequestListener() {
                @Override
                public void onSuccess(Object response) {
                    JSONObject r = ((JSONObject) response);
                    try{
                        if(!r.getString("message").isEmpty()){
                            System.out.println(r.getString("message"));
                            game.board.removeWall(x, y, dir);
                            game.currentPlayer = myPlayer;
                            //throw new RuntimeException("Server and client disagree with Place_Wall.");
                        }else{
                            loadCont.FinishLoading();
                        }
                    }catch (JSONException e){
                        throw new RuntimeException("Unable to parse server board JSON object.");
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    System.out.println("Unable to connect to server!");
                    throw new RuntimeException(errorMessage);
                }
            });

            return true;
        }
        return false;
    }
}
