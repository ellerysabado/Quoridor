package com.example.quoridor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quoridor.R;
import com.example.quoridor.ui.Lobby.LobbyPreselectionScreen;
import com.example.quoridor.ui.game.gameScreen;
import com.example.quoridor.ui.gameHistory.MainGameHistoryUi;


/**
 * Start screen navigation activity that has buttons for
 * <ul>
 *     <li>Start Game - Going to lobby preselection screen.</li>
 *     <li>Account - Going to the login screen or the account view.</li>
 *     <li>Leaderboard - Going to the leaderboard screen.</li>
 *     <li>Settings - Going to the settings screen.</li>
 *     <li>Exit - Exits the game.</li>
 * </ul>
 *
 * @author Carter Awbrey
 * */
public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        Button exitButton = findViewById(R.id.exitButton);
        Button startGameButton = findViewById(R.id.startGameButton);
        Button goToAccountButton = findViewById(R.id.accountButton);
        Button leaderboardButton = findViewById(R.id.leaderboardButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        SharedPreferences sp = getSharedPreferences("account", MODE_PRIVATE);


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean loggedIn = true;

                if(sp.contains("logged_in") && sp.getBoolean("logged_in", loggedIn)){
                    startActivity(new Intent(StartScreen.this, LobbyPreselectionScreen.class));
                    System.out.println("Already Logged In: going to lobby preselection");
                }else{
                    System.out.println("Not Logged In: Going to the Login Screen");
                    startActivity(new Intent(StartScreen.this, LoginScreen.class));
                    Toast.makeText(getApplicationContext(), "Error: Not Logged In", Toast.LENGTH_LONG).show();
                }
            }
        });

        goToAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean loggedIn = true;

                if(sp.contains("logged_in") && sp.getBoolean("logged_in", loggedIn)){
                    startActivity(new Intent(StartScreen.this, AccountScreen.class));
                }else{
                    startActivity(new Intent(StartScreen.this, LoginScreen.class));
                }
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreen.this, MainGameHistoryUi.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreen.this, gameScreen.class));
            }
        });
    }
}