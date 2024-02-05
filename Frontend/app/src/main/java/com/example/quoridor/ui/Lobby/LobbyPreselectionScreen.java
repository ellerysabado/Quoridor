package com.example.quoridor.ui.Lobby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quoridor.R;
import com.example.quoridor.data.game.lobby.Lobby;
import com.example.quoridor.ui.game.LocalGameScreen;
import com.example.quoridor.utils.ErrorOutputter;
import com.example.quoridor.utils.LoadingController;


/**
 * Lobby preselection screen that allows the user to choose which type of game they would
 * like to start.
 *
 * Available options are creating a local game, joining a lobby, or creating a lobby.
 *
 * @author Carter Awbrey
* */
public class LobbyPreselectionScreen extends AppCompatActivity {
    private ProgressBar loadingProgressBar;

    private Lobby lobby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_preselection);

        loadingProgressBar = findViewById(R.id.loading_lobby_preselect);

        EditText sessionEditor = findViewById(R.id.lobby_code_editor);

        final String userId = getSharedPreferences("account",MODE_PRIVATE).getString("id", new String(""));
        final String username = getSharedPreferences("account",MODE_PRIVATE).getString("username", new String(""));
        System.out.println("User Id: " + userId);
        System.out.println("Username: " + username);

        ErrorOutputter defaultErrorOut = new ErrorOutputter() {
            @Override
            public void notifyException(String string) {
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void silentException(String string) {
                System.out.println("----ERROR: " + string);
            }
        };
        LoadingController loadingCont = new LoadingController() {
            @Override
            public void onCreate() {
                //lobby has been properly initialized moving to real lobby screen
                Intent destIntent = new Intent(LobbyPreselectionScreen.this, LobbyScreen.class);
                destIntent.putExtra("lobby", lobby);
                startActivity(destIntent);
            }

            @Override
            public void StartLoading() {
                loadingProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void FinishLoading() {
                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        };

        findViewById(R.id.join_lobby_button).setOnClickListener(view -> {

            if(sessionEditor.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Lobby Code Required", Toast.LENGTH_LONG).show();
            }else{
                lobby = new Lobby(userId, sessionEditor.getText().toString(), username, defaultErrorOut, loadingCont);
            }
        });

        findViewById(R.id.create_lobby_button).setOnClickListener(view -> {
            lobby = new Lobby(userId, username, defaultErrorOut, loadingCont);
        });

        findViewById(R.id.create_local_game_button).setOnClickListener(view ->{
            startActivity(new Intent(LobbyPreselectionScreen.this, LocalGameScreen.class));
        });
    }
}