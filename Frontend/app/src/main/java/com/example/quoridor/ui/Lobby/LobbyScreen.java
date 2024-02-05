package com.example.quoridor.ui.Lobby;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quoridor.R;
import com.example.quoridor.data.game.lobby.Lobby;
import com.example.quoridor.ui.StartScreen;
import com.example.quoridor.ui.game.gameScreen;
import com.example.quoridor.utils.ErrorOutputter;
import com.example.quoridor.utils.LoadingController;

import org.json.JSONException;

/**
 * Lobby screen that allows the users to wait for a game to start.
 * If the current player is a host, they are able to start the game, adjust game settings,
 * and kicking players from the lobby.
 * Upon the host starting the game this view automatically changes to the main game view.
 *
 * @author Carter Awbrey
 * */
public class LobbyScreen extends AppCompatActivity {
    private Lobby lobby;
    private ProgressBar loadingCircle;

    private static final int updateInterval = 500;
    private int numUpdates;

    private Handler lobbyNetUpdater;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        loadingCircle = findViewById(R.id.loading_lobby);

        numUpdates = 0;
        lobbyNetUpdater = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                numUpdates++;
                System.out.println("Net Update: " + numUpdates);
                System.out.println("Lobby State: " + lobby.getLobbyState());

                switch (lobby.getLobbyState()){
                    case -1:
                        //System.exit(0);
                        finish();
                        break;
                    case 0:
                        //game started: goto game screen
                        Intent gameIntent = new Intent(LobbyScreen.this, gameScreen.class);
                        gameIntent.putExtra("session", lobby.getSession());
                        startActivity(gameIntent);
                        finish();
                        break;
                    case 1:
                        //lobby in session, update the lobby here
                        lobby.update();
                        lobbyNetUpdater.postDelayed(this, updateInterval);
                        break;
                    default:
                        //lobby ended or player was kicked, goto main screen
                        lobbyNetUpdater.removeMessages(0);
                        startActivity(new Intent(LobbyScreen.this, StartScreen.class));
                        finish();
                        break;
                }
            }
        };

        lobby = getIntent().getParcelableExtra("lobby");
        lobby.exceptions = new ErrorOutputter() {
            @Override
            public void notifyException(String string) {
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void silentException(String string) {
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
            }
        };
        lobby.loadingController = new LoadingController() {
            @Override
            public void onCreate() {
                lobbyNetUpdater.postDelayed(runnable, updateInterval);
                AssignPrivates();
                UpdateUIElements();
            }

            @Override
            public void StartLoading() {
                loadingCircle.setVisibility(View.VISIBLE);
            }

            @Override
            public void FinishLoading() {
                loadingCircle.setVisibility(View.INVISIBLE);
                if(lobby.getLobbyState() != -1){
                    UpdateUIElements();
                }
            }
        };

        lobby.init();
    }

    @Override
    public void onBackPressed(){
        lobby.leaveLobby();
    }

    private void AssignPrivates(){
        ((TextView) findViewById(R.id.lobby_code_textView)).setText(String.format("%s %s",
                ((TextView) findViewById(R.id.lobby_code_textView)).getText(), lobby.getSession()));

        findViewById(R.id.host_kick_player_1).setOnClickListener(view -> lobby.kickPlayer(0));
        findViewById(R.id.host_kick_player_2).setOnClickListener(view -> lobby.kickPlayer(1));
        findViewById(R.id.host_kick_player_3).setOnClickListener(view -> lobby.kickPlayer(2));
        findViewById(R.id.host_kick_player_4).setOnClickListener(view -> lobby.kickPlayer(3));

        findViewById(R.id.host_make_host_player_1).setOnClickListener(view -> {
            try {
                if(lobby.getSettings().getInt("host") != 0){
                    lobby.changeSetting("host", 0);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });
        findViewById(R.id.host_make_host_player_2).setOnClickListener(view -> {
            try {
                if(lobby.getSettings().getInt("host") != 1){
                    lobby.changeSetting("host", 1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });
        findViewById(R.id.host_make_host_player_3).setOnClickListener(view -> {
            try {
                if(lobby.getSettings().getInt("host") != 2){
                    lobby.changeSetting("host", 2);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });
        findViewById(R.id.host_make_host_player_4).setOnClickListener(view -> {
            try {
                if(lobby.getSettings().getInt("host") != 3){
                    lobby.changeSetting("host", 3);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });
        findViewById(R.id.host_player_number_toggle).setOnClickListener(view -> {
            if(((Switch) findViewById(R.id.host_player_number_toggle)).isChecked()){
                lobby.changeSetting("player_count", 4);
            }else{
                lobby.changeSetting("player_count", 2);
            }
        });
        findViewById(R.id.host_start_game_button).setOnClickListener(view -> lobby.startGame());

        findViewById(R.id.leave_lobby_button).setOnClickListener(view -> lobby.leaveLobby());
    }

    private void UpdateUIElements(){
        UpdateUserView();
        UpdatePlayersUI();
    }

    //changes the current user view to
    private void UpdateUserView(){
        if(!lobby.amHost()){
            //not a host: setting the host tools to invisible
            findViewById(R.id.host_start_game_button).setVisibility(View.GONE);
            findViewById(R.id.host_kick_player_1).setVisibility(View.GONE);
            findViewById(R.id.host_kick_player_2).setVisibility(View.GONE);
            findViewById(R.id.host_kick_player_3).setVisibility(View.GONE);
            findViewById(R.id.host_kick_player_4).setVisibility(View.GONE);
            findViewById(R.id.host_player_number_toggle).setVisibility(View.GONE);

            findViewById(R.id.host_make_host_player_1).setVisibility(View.GONE);
            findViewById(R.id.host_make_host_player_2).setVisibility(View.GONE);
            findViewById(R.id.host_make_host_player_3).setVisibility(View.GONE);
            findViewById(R.id.host_make_host_player_4).setVisibility(View.GONE);
        }else{
            //am a host: settings the host tools to visible
            findViewById(R.id.host_start_game_button).setVisibility(View.VISIBLE);
            findViewById(R.id.host_kick_player_1).setVisibility(View.VISIBLE);
            findViewById(R.id.host_kick_player_2).setVisibility(View.VISIBLE);
            findViewById(R.id.host_kick_player_3).setVisibility(View.VISIBLE);
            findViewById(R.id.host_kick_player_4).setVisibility(View.VISIBLE);
            findViewById(R.id.host_player_number_toggle).setVisibility(View.VISIBLE);

            findViewById(R.id.host_make_host_player_1).setVisibility(View.VISIBLE);
            findViewById(R.id.host_make_host_player_2).setVisibility(View.VISIBLE);
            findViewById(R.id.host_make_host_player_3).setVisibility(View.VISIBLE);
            findViewById(R.id.host_make_host_player_4).setVisibility(View.VISIBLE);

            UpdateSettings();
        }
    }

    //updates the UI elements associated with the players given the players JSON array specified in the API
    private void UpdatePlayersUI(){
        for(int i = 0; i < 4; i++){
            TableRow currentRow;
            TextView currentPlayerName;
            Button currentHostButton;
            Button currentKickButton;
            switch (i){
                case 0:
                    currentRow = findViewById(R.id.player1_row);
                    currentPlayerName = findViewById(R.id.player1_textView);
                    currentHostButton = findViewById(R.id.host_make_host_player_1);
                    currentKickButton = findViewById(R.id.host_kick_player_1);
                    break;

                case 1:
                    currentRow = findViewById(R.id.player2_row);
                    currentPlayerName = findViewById(R.id.player2_textView);
                    currentHostButton = findViewById(R.id.host_make_host_player_2);
                    currentKickButton = findViewById(R.id.host_kick_player_2);
                    break;

                case 2:
                    currentRow = findViewById(R.id.player3_row);
                    currentPlayerName = findViewById(R.id.player3_textView);
                    currentHostButton = findViewById(R.id.host_make_host_player_3);
                    currentKickButton = findViewById(R.id.host_kick_player_3);
                    break;

                default:
                    currentRow = findViewById(R.id.player4_row);
                    currentPlayerName = findViewById(R.id.player4_textView);
                    currentHostButton = findViewById(R.id.host_make_host_player_4);
                    currentKickButton = findViewById(R.id.host_kick_player_4);
                    break;
            }

            if(i > lobby.getPlayers().size() - 1){
                currentRow.setVisibility(View.GONE);
            }else {
                if(lobby.getPlayers().get(i).role.equals("host")){
                    //adding crown emoji to the host
                    currentPlayerName.setText(String.format("\uD83D\uDC51 %s", lobby.getPlayers().get(i).name));
                    currentHostButton.setVisibility(View.GONE);
                    currentKickButton.setVisibility(View.GONE);
                }else{
                    //adding green dot to clients
                    currentPlayerName.setText(String.format("\uD83D\uDFE2 %s", lobby.getPlayers().get(i).name));
                    if(lobby.amHost()){
                        currentHostButton.setVisibility(View.VISIBLE);
                        currentKickButton.setVisibility(View.VISIBLE);
                    }

                }
                currentRow.setVisibility(View.VISIBLE);
            }
        }
    }

    private void UpdateSettings(){
        try{
            ((Switch) findViewById(R.id.host_player_number_toggle)).setChecked(
                    lobby.getSettings().getInt("player_count") == 4);
        }catch (JSONException ignored){
            System.out.println("Error: Unable to Change Player Count");
        }
    }
}