package com.example.quoridor.data.game.game;

import com.android.volley.Request;
import com.example.quoridor.utils.net.RequestListener;
import com.example.quoridor.utils.net.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
*
 * Class that implements all of the server side API calls for the main game.
 * @author Carter Awbrey
* */
public class GameNet {

    /**
    * Implements the GetBoard API call that returns the board from the backend
     * @param listener the request listener interface that answers when the API call is returned
     * @param session the session id of the game
     * @param uuid the uuid of the user making the API call
    * */
    public static void GetBoard(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "GETBOARD");
            LobbyPacket.put("data", new JSONObject());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/game", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Implements the GetPlayers API call that returns the list of players from the backend
     * @param listener the request listener interface that answers when the API call is returned
     * @param session the session id of the game
     * @param uuid the uuid of the user making the API call
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
        VolleyListener.makeRequest("/game", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Implements the PlaceWall API call that places a wall and returns an error message if there is one
     * @param listener the request listener interface that answers when the API call is returned
     * @param session the session id of the game
     * @param uuid the uuid of the user making the API call
     * @param x x position of the wall
     * @param y y position of the wall
     * @param dir direction of the placed wall(0=horizontal, 1=vertical)
     * */
    public static void PlaceWall(String uuid, String session, int x, int y, int dir, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        JSONObject innerPacket = new JSONObject();
        try {
            innerPacket.put("x", x);
            innerPacket.put("y", y);
            innerPacket.put("direction", dir);

            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "PLACEWALL");
            LobbyPacket.put("data", innerPacket);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/game", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Implements the MovePlayer API call that moves the player and returns an error if there is one
     * @param listener the request listener interface that answers when the API call is returned
     * @param session the session id of the game
     * @param uuid the uuid of the user making the API call
     * @param x1 player jumped x position
     * @param y1 player jumped y position
     * @param x2 destination x position
     * @param y2 destination y position
     * */
    public static void MovePlayer(String uuid, String session, int x1, int y1, int x2, int y2,RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        JSONObject innerPacket = new JSONObject();
        try {
            innerPacket.put("x1", x1);
            innerPacket.put("y1", y1);
            innerPacket.put("x2", x2);
            innerPacket.put("y2", y2);

            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "MOVEPLAYER");
            LobbyPacket.put("data", innerPacket);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/game", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Implements the MovePlayer API call that moves the player by two spaces and returns an error if there is one
     * @param listener the request listener interface that answers when the API call is returned
     * @param session the session id of the game
     * @param uuid the uuid of the user making the API call
     * @param x destination x position
     * @param y destination y position
     * */
    public static void MovePlayer(String uuid, String session, int x, int y, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        JSONObject innerPacket = new JSONObject();
        try {
            innerPacket.put("x1", x);
            innerPacket.put("y1", y);

            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "MOVEPLAYER");
            LobbyPacket.put("data", innerPacket);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/game", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * Implements the GetSettings API call that returns JSON object with all of the settings of the game
     * @param listener the request listener interface that answers when the API call is returned
     * @param session the session id of the game
     * @param uuid the uuid of the user making the API call
     * */
    public static void GetSettings(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        JSONObject innerPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "GETSETTINGS");
            LobbyPacket.put("data", innerPacket);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/game", listener, LobbyPacket, Request.Method.POST);
    }

    /**
     * @deprecated
     * Implements the GameChat API call that returns the board from the backend
     * @param listener the request listener interface that answers when the API call is returned
     * @param session the session id of the game
     * @param uuid the uuid of the user making the API call
     * */
    public static void GameChat(String uuid, String session, RequestListener listener) {
        JSONObject LobbyPacket = new JSONObject();
        JSONObject innerPacket = new JSONObject();
        try {
            LobbyPacket.put("uuid", uuid);
            LobbyPacket.put("session", session);
            LobbyPacket.put("function", "SENDCHAT");
            LobbyPacket.put("data", innerPacket);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/chat", listener, LobbyPacket, Request.Method.POST);
    }
}
