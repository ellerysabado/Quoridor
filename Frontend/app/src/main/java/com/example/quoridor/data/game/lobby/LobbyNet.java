package com.example.quoridor.data.game.lobby;

import com.android.volley.Request;
import com.example.quoridor.utils.net.RequestListener;
import com.example.quoridor.utils.net.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
* Class that implements all of the server side API calls for the lobby.
 * @author Carter Awbrey
* */
public class LobbyNet {

    /**
    * Creates a lobby on the backend, returns the session id within the request listener.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
    * */
    public static void CreateLobby(String uuid, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("function", "CREATELOBBY");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Joins an existing lobby on the backend, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * */
    public static void JoinLobby(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "JOINLOBBY");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Attempts to retrieve the players array from the server, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * */
    public static void GetPlayers(String uuid, String session, RequestListener listener) {

        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "GETPLAYERS");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Attempts to retrieve the lobby state from the server, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * */
    public static void GetLobbyState(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "GETLOBBYSTATE");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Attempts to leave the lobby on the server, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * */
    public static void LeaveLobby(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "LEAVELOBBY");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * @apiNote Only works if the client is a host or admin.
     * Attempts to kick a player from the current lobby, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * @param playerNumber number id of player to kick from the server
     * */
    public static void KickPlayer(String uuid, String session, int playerNumber, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "KICKPLAYER");
            LobbyPacket.put("data", new JSONObject());
            LobbyPacket.getJSONObject("data").put("player", playerNumber);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * @apiNote Only works if the client is a host or admin.
     * Attempts to start the game of the current lobby, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * */
    public static void StartGame(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "STARTGAME");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * @apiNote Only works if the client is a host or admin.
     * Attempts to set the settings of the specified lobby, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * @param settings complete JSON array of settings
     * */
    public static void ChangeSettings(String uuid, String session, JSONObject settings, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        JSONObject filteredSettings = new JSONObject();


        try {
            filteredSettings.put("player_count", settings.getInt("player_count"));
            filteredSettings.put("host", settings.getInt("host"));
            filteredSettings.put("botCount", settings.getInt("botCount"));


            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "CHANGESETTINGS");
            LobbyPacket.put("data", filteredSettings);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * @apiNote only returns if the lobby is actually started and the client is part of the lobby.
     * Attempts get the json settings from the specified lobby, returns an error if there is a problem.
     * @param uuid uuid of the client
     * @param listener listener interface for handling the response
     * @param session session id of desired lobby session.
     * */
    public static void GetSettings(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "GETSETTINGS");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/lobby", listener, LobbyPacket, Request.Method.POST);
    }

}
