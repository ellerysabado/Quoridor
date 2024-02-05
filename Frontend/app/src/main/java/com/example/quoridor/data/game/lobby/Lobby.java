package com.example.quoridor.data.game.lobby;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.example.quoridor.utils.ErrorOutputter;
import com.example.quoridor.utils.LoadingController;
import com.example.quoridor.utils.net.RequestListener;
import com.example.quoridor.data.game.game.Player;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
*
* Class that handles a networked game lobby with the server.
 * @author Carter Awbrey
* */
public class Lobby implements Parcelable {

    private final String uuid;
    private final String username;
    private String session;
    private int lobby_state;
    private ArrayList<Player> players;
    private JSONObject settings;

    public ErrorOutputter exceptions;
    public LoadingController loadingController;


    /**
    * Constructor for the lobby, used for CREATING a new lobby.
     *
     * @param uuid UUID of client player
     * @param username Username of client player
     * @param exceptions error handler interface that deals with notifications from the server
     * @param loadingController loading controller interface that informs when the lobby is retrieving data
    * */
    public Lobby(String uuid, String username, ErrorOutputter exceptions, LoadingController loadingController) {
        this.uuid = uuid;
        this.username = username;
        this.exceptions = exceptions;
        this.loadingController = loadingController;
        this.lobby_state = -1;

        loadingController.StartLoading();
        LobbyNet.CreateLobby(uuid, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                try {
                    if (((JSONObject) response).getString("message").isEmpty()) {
                        session = ((JSONObject) response).getString("session");
                        init();
                        loadingController.FinishLoading();
                    } else {
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                        loadingController.FinishLoading();
                    }
                } catch (JSONException e) {
                    exceptions.silentException("Error: Unable to Parse Session ID");
                    loadingController.FinishLoading();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                exceptions.notifyException("Error: Unable to Connect To Server");
                loadingController.FinishLoading();
            }
        });
    }

    /**
     * Constructor for the lobby, used for JOINING an existing lobby.
     *
     * @param uuid UUID of client player
     * @param username Username of client player
     * @param session session of lobby that you are trying to join
     * @param exceptions error handler interface that deals with notifications from the server
     * @param loadingController loading controller interface that informs when the lobby is retrieving data
     * */
    public Lobby(String uuid, String session, String username, ErrorOutputter exceptions, LoadingController loadingController) {
        this.uuid = uuid;
        this.session = session;
        this.username = username;
        this.exceptions = exceptions;
        this.loadingController = loadingController;
        this.lobby_state = -1;
        loadingController.StartLoading();
        LobbyNet.JoinLobby(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                try {
                    if (((JSONObject) response).getString("message").isEmpty()) {
                        init();
                        loadingController.FinishLoading();
                    } else {
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                        loadingController.FinishLoading();
                    }
                } catch (JSONException e) {
                    exceptions.silentException("Error: Unable to Parse Session ID");
                    loadingController.FinishLoading();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                exceptions.notifyException("Error: Unable to Connect To Server");
                loadingController.FinishLoading();
            }
        });
        init();
    }

    /**
     * @apiNote in order to user the parcelable constructor the lobby must already be joined on the server
    * Constructor to enable the use of parcelable interface.
    * */
    protected Lobby(Parcel in) {
        uuid = in.readString();
        username = in.readString();
        session = in.readString();
        lobby_state = in.readInt();
    }

    public static final Creator<Lobby> CREATOR = new Creator<Lobby>() {
        @Override
        public Lobby createFromParcel(Parcel in) {
            return new Lobby(in);
        }

        @Override
        public Lobby[] newArray(int size) {
            return new Lobby[size];
        }
    };

    /**
     * @apiNote does not update the lobby if it is ended.
    * Updates all current variables in the lobby.
    * */
    public void update() {
        loadingController.StartLoading();

        if(lobby_state != 3){
            updateSettings();
            updatePlayers();
            updateLobbyState();
        }

        loadingController.FinishLoading();
    }

    /**
    * Used to get the settings for the lobby, if active.
    *
    * @return JSON object containing all of the current settings
    * */
    public JSONObject getSettings() {
        return settings;
    }

    /**
    * Used to determine whether the game is 4 player or not.
     *
     * @return true=4player, false=2player
    * */
    public boolean is4PlayerGame() {
        try {
            return settings.getInt("num_players") == 4;
        } catch (JSONException e) {
            exceptions.silentException("Error: Unable to Parse Settings");
        }
        return false;
    }

    /**
    *
     * Function to determine whether the lobby is still active(i.e. still waiting for the game to start)
     * @return true=lobbyActive, false=lobbyInactive
    * */
    public boolean waiting() {
        return lobby_state == 1;
    }

    /**
    * Retrieves the current lobby state.
     *
     * @return integer representing lobby state 0=gameStarted 1=lobbyWaiting 2=kickedETC.
    * */
    public int getLobbyState() {
        return lobby_state;
    }

    /**
    * @return Arraylist of players currently waiting in the lobby.
    * */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @apiNote Players can only be kicked if the client has sufficient permissions.
    * Kicks a player from the current lobby
    * */
    public void kickPlayer(int playerNumber) {
        if (playerNumber == findClientPlayer()) {
            System.out.println("Can't Kick Yourself");
            return;
        }
        loadingController.StartLoading();

        LobbyNet.KickPlayer(uuid, session, playerNumber, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                loadingController.FinishLoading();
                try{
                    if(((JSONObject) response).getString("message").isEmpty()){
                        updatePlayers();
                    }else{
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                    }
                }catch (JSONException e){
                    exceptions.silentException("Error: Unable to Parse Session ID");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingController.FinishLoading();
                exceptions.notifyException("Error: Unable to Connect To Server");
            }
        });
    }


    /*
    * Leaves the current lobby, should be called if the player leaves the lobby.
    * */
    public void leaveLobby() {
        loadingController.StartLoading();
        LobbyNet.LeaveLobby(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                loadingController.FinishLoading();
                try{
                    if(((JSONObject) response).getString("message").isEmpty()){
                        lobby_state = 3;
                    }else{
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                    }
                }catch (JSONException e){
                    exceptions.silentException("Error: Unable to Parse Session ID");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingController.FinishLoading();
                exceptions.notifyException("Error: Unable to Connect To Server");
            }
        });
    }

    /**
     * @apiNote Client can only change settings if they have sufficient permissions.
     * Changes the settings of the lobby
     * @param newSettings JSON object with all of the new settings
    * */
    public void changeSettings(JSONObject newSettings) {
        loadingController.StartLoading();
        LobbyNet.ChangeSettings(uuid, session, newSettings, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                loadingController.FinishLoading();
                try {
                    if (!((JSONObject) response).getString("message").isEmpty()) {
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                    }
                } catch (JSONException e) {
                    exceptions.silentException("Error: Unable to Parse Session ID");
                }

                updateSettings();
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingController.FinishLoading();
                exceptions.notifyException("Error: Unable to Connect To Server");
            }
        });
    }

    /**
    * Changes a single setting in the settings object, and submits the change to the server.
     *
     * @param key the name of the settings
     * @param value the value of the setting
    * */
    public void changeSetting(String key, int value){
        try {
            settings.put(key, value);
            changeSettings(settings);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }

        changeSettings(settings);
    }

    /**
     * @apiNote Only players with sufficient permissions can start the game.
    * Starts the actual game, I.E. stopping the lobby.
    * */
    public void startGame() {
        loadingController.StartLoading();
        LobbyNet.StartGame(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                try {
                    if (!(((JSONObject) response).getString("message").isEmpty())) {
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                    }
                } catch (JSONException e) {
                    exceptions.silentException("Error: Unable to Parse Session ID");
                }
                loadingController.FinishLoading();
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingController.FinishLoading();
                exceptions.notifyException("Error: Unable to Connect To Server");
            }
        });
    }

    /**
    * Pulls all new player and settings data from the server.
    * */
    public void init() {
        players = new ArrayList<>();
        settings = new JSONObject();
        update();
    }

    /**
    * Updates the settings on the server.
    * */
    private void updateSettings() {
        LobbyNet.GetSettings(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                try {
                    if (((JSONObject) response).has("player_count")) {
                        settings = ((JSONObject) response);
                    } else if(((JSONObject) response).getString("message").equals("Not In Game")){
                        lobby_state = 3;
                    }else{
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException("Unable to Parse Settings From Server");
                }
            }

            @Override
            public void onFailure(String errorMessage){
                exceptions.notifyException("Error: Unable to Connect To Server");
            }
        });
    }

    /**
    * Updates the players array on the server.
    * */
    private void updatePlayers() {
        LobbyNet.GetPlayers(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                try {
                    if (((JSONObject) response).getString("message").isEmpty()) {
                        players.clear();
                        for (int i = 0; i < ((JSONObject) response).getJSONArray("players").length(); i++) {
                            JSONObject temp = ((JSONObject) response).getJSONArray("players").getJSONObject(i);
                            players.add(new Player(temp.getString("name"), temp.getString("role")));
                        }
                    } else if(((JSONObject) response).getString("message").equals("Not In Game")){
                        lobby_state = 3;
                    }else{
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException("Unable to Parse Players From Server");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                exceptions.notifyException("Error: Unable to Connect To Server");
            }
        });
    }

    /*
    * Updates the local lobby state by fetching from the server.
    * */
    private void updateLobbyState() {
        LobbyNet.GetLobbyState(uuid, session, new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                loadingController.FinishLoading();
                try {
                    if (((JSONObject) response).getString("message").isEmpty()) {
                        if(lobby_state == -1){
                            loadingController.onCreate();
                        }
                        if(lobby_state != 3){
                            lobby_state = ((JSONObject) response).getInt("lobby_state");
                        }
                    } else {
                        exceptions.notifyException(((JSONObject) response).getString("message"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException("Unable to Parse Lobby State From Server");
                }

            }

            @Override
            public void onFailure(String errorMessage) {
                exceptions.notifyException("Error: Unable to Connect To Server");
            }
        });
    }

    /**
     * Simple function to find which player in the player array represents the local player.
    * @return index of the local client player
    * */
    public int findClientPlayer() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).name.equals(username)) {
                return i;
            }
        }

        return -69;
    }

    /**
     * Simple function to determine whether the client is the lobby host.
    * @return true=amHost false=notHost
    * */
    public boolean amHost(){
        return players.get(findClientPlayer()).role.equals("host");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(username);
        parcel.writeString(session);
        parcel.writeInt(lobby_state);
    }

    /**
    * @return UUID of client player
    * */
    public String getUUID(){
        return uuid;
    }

    /**
     * @return username of client player
    * */
    public String getUsername(){
        return username;
    }

    /**
     * @apiNote if this is an empty string there was likely an uncaught exception within the backend.
     * @return session id of the lobby
    * */
    public String getSession(){
        return session;
    }
}
