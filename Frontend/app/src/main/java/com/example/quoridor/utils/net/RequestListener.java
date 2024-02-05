package com.example.quoridor.utils.net;


/**
 * Interface for responding to volley request made through the VolleyListener class.
 *
* @author Carter Awbrey
* */
public interface RequestListener{
    public void onSuccess(Object response);
    public void onFailure(String errorMessage);
}


