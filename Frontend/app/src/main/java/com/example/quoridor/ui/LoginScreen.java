package com.example.quoridor.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.example.quoridor.R;
import com.example.quoridor.utils.net.RequestListener;
import com.example.quoridor.utils.net.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;


/**
 * Login activity that negotiates login or registration with the backend,
 * and forward to the account screen upon completion.
 *
 * @author Carter Awbrey
 * */
public class LoginScreen extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar loadingProgressBar;

    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditor);
        passwordEditText = findViewById(R.id.passwordEditor);
        Button loginButton = findViewById(R.id.loginButton);
        loadingProgressBar = findViewById(R.id.loading);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> loginRequest(usernameEditText.getText().toString(), passwordEditText.getText().toString()));

        registerButton.setOnClickListener(v -> {
            if(isValidPassword(usernameEditText.getText().toString())){
                registerRequest(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Weak Password", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loginRequest(String username, String password){
        loadingProgressBar.setVisibility(View.VISIBLE);
        RequestListener loginListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                loadingProgressBar.setVisibility(View.INVISIBLE);
                try {
                    if(!((JSONObject) response).get("error").toString().isEmpty()){
                        throw new LoginException(((JSONObject) response).get("error").toString());
                    }else{
                        SharedPreferences sp = getSharedPreferences("account",MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();

                        ed.putBoolean("logged_in", true);
                        ed.putString("username", username);
                        ed.putString("id", ((JSONObject) response).getString("id"));
                        ed.apply();
                        gotoAccountScreen();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: Invalid User or Password", Toast.LENGTH_LONG).show();
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Error: Unable to Connect to Server", Toast.LENGTH_LONG).show();
                System.out.println(errorMessage);
            }
        };

        JSONObject loginPacket = new JSONObject();

        try {
            loginPacket.put("login", "true");
            loginPacket.put("username", username);
            loginPacket.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        VolleyListener.makeRequest("/login", loginListener, loginPacket, Request.Method.POST);
    }

    private void registerRequest(String username, String password){
        loadingProgressBar.setVisibility(View.VISIBLE);

        RequestListener registrationListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                loadingProgressBar.setVisibility(View.INVISIBLE);

                try {
                    if(!((JSONObject) response).get("error").toString().isEmpty()){
                        throw new LoginException(((JSONObject) response).get("error").toString());
                    }else{
                        SharedPreferences sp = getSharedPreferences("account",MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();

                        ed.putBoolean("logged_in", true);
                        ed.putString("id", ((JSONObject) response).getString("id"));
                        ed.putString("username", username);
                        ed.apply();

                        Toast.makeText(getApplicationContext(), "ID: " + ((JSONObject) response).getString("id"), Toast.LENGTH_SHORT).show();

                        gotoAccountScreen();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Error: Unable to Connect to Server", Toast.LENGTH_LONG).show();
                System.out.println(errorMessage);
            }
        };



        JSONObject registerPacket = new JSONObject();
        try {
            registerPacket.put("login", "false");
            registerPacket.put("username", username);
            registerPacket.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        VolleyListener.makeRequest("/login", registrationListener, registerPacket, Request.Method.POST);
    }

    public static boolean isValidPassword(String password)
    {
        if(password.isEmpty())
            return false;

        if(password.length() < 6)
            return false;

        return !password.contains(" ");
    }

    private void gotoAccountScreen(){
        startActivity(new Intent(LoginScreen.this, AccountScreen.class));
    }
}
