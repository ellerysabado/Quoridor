package com.example.quoridor.utils;


/**
 * Interface for responding to errors within volley request, used primarily
 * by the GameNet, and the LobbyNet.
 *
 * @author Carter Awbrey
 * */
public interface ErrorOutputter {

    public void notifyException(String string);
    public void silentException(String string);
}
