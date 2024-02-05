package com.example.quoridor.ui.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quoridor.R;
import com.example.quoridor.data.game.game.Player;
import com.example.quoridor.data.game.game.QuoridorEngine;

public class LocalGameScreen extends AppCompatActivity {

    private QuoridorBoard gameUI;
    private QuoridorEngine gameEng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_game_screen);


        Player[] players = new Player[2];

        players[0] = new Player("Quori-BOT", "Host", 0, 4, 0);
        players[1] = new Player("You", "Client", 1, 4, 8);

        gameEng = new QuoridorEngine(players, 0);
        gameUI = findViewById(R.id.localGameBoard);

        gameUI.boardClickListener = new BoardOnClick() {
            @Override
            public void clickWall(int x, int y, int dir) {
                if(gameEng.Place_Wall(gameEng.Get_Current_Player(), x, y, dir)) {
                    update_board_ui();
                }
                if(gameEng.Get_Game_State() != 0){
                    displayGameOver();
                }
            }

            @Override
            public void clickTile(int x, int y) {
                if (gameEng.Move_Player(gameEng.Get_Current_Player(), x, y)) {
                    update_board_ui();
                }
                if(gameEng.Get_Game_State() != 0){
                    displayGameOver();
                }
            }
        };

        update_board_ui();


    }

    private void update_board_ui(){
        gameUI.players = gameEng.Get_Players_List();
        gameUI.walls = gameEng.Get_Walls_ArrayList();
        gameUI.altTiles = gameEng.Get_Available_Moves(gameEng.Get_Current_Player());
        gameUI.invalidate();
    }

    private void displayGameOver(){
        ((TextView) findViewById(R.id.winnerText)).setText(gameEng.Get_Players_List().get(gameEng.Get_Game_State()).name + " Won!");

        findViewById(R.id.returnButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        findViewById(R.id.winnerCard).setVisibility(View.VISIBLE);
    }
}