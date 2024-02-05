package com.example.quoridor.utils.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.quoridor.data.constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Listener for creating Volley data requests.
 *
 * @author Carter Awbrey
 * */
public class VolleyListener {
    private static final String baseUrl = constants.server_url;

    /**
    * Make a request from the server url, and receives JSON object.
     *
    *  @param path path to send request to
     * @param requestListener interface to handle packet responses
     * @param data data to send in the POST type packet
     * @param method http request type
    * */
    public static void makeRequest(String path, RequestListener requestListener, JSONObject data, int method){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, baseUrl + path, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestListener.onFailure(error.getMessage());
                    }
                }){
                    //Setting the request headers to be in JSON formatting
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
        RequestController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
