package com.example.quoridor.ui.game;

import static com.example.quoridor.R.layout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.quoridor.R;
import com.example.quoridor.data.game.game.OnlineQuoridorGame;
import com.example.quoridor.utils.LoadingController;


/**
 * Controller class for main game screen. It contains a QuoridorBoard element.
 *
 * @author Carter Awbrey
 * @// TODO: 4/27/23 Add player views and game control mechanisms.
* */
public class gameScreen extends AppCompatActivity {
    private String uuid;
    private String username;
    private String session;
    private QuoridorBoard boardUI;
    private AppCompatButton[] playerTags;

    private OnlineQuoridorGame game;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game_screen);
        uuid = getSharedPreferences("account", MODE_PRIVATE).getString("id", "");
        username = getSharedPreferences("account", MODE_PRIVATE).getString("username", "");
        session = getIntent().getStringExtra("session");

        boardUI = findViewById(R.id.gameBoard);
        playerTags = new AppCompatButton[4];
        playerTags[0] = findViewById(R.id.player0Tag);
        playerTags[1] = findViewById(R.id.player1Tag);
        playerTags[2] = findViewById(R.id.player2Tag);
        playerTags[3] = findViewById(R.id.player3Tag);
        playerTags[0].setBackgroundTintList(getColorStateList(R.color.Light_Blue));
        playerTags[1].setBackgroundTintList(getColorStateList(R.color.Pale_Gray));
        playerTags[2].setBackgroundTintList(getColorStateList(R.color.Pale_Gray));
        playerTags[3].setBackgroundTintList(getColorStateList(R.color.Pale_Gray));


        game = new OnlineQuoridorGame(uuid, username, session, new LoadingController() {
            @Override
            public void onCreate() {
            }
            @Override
            public void StartLoading() {
            }
            @Override
            public void FinishLoading() {
                if(game.game.Get_Current_Player() == game.myPlayer){
                    boardUI.altTiles = game.game.Get_Available_Moves(game.myPlayer);
                }else{
                    boardUI.altTiles.clear();
                }

                boardUI.players = game.game.Get_Players_List();

                boardUI.walls = game.game.Get_Walls_ArrayList();

                boardUI.invalidate();

                playerTags[0].setText(game.game.players[0].name);
                playerTags[1].setText(game.game.players[1].name);
                playerTags[0].setBackgroundTintList(getColorStateList(R.color.Pale_Gray));
                playerTags[1].setBackgroundTintList(getColorStateList(R.color.Pale_Gray));


                if(game.game.players.length == 4){
                    playerTags[2].setText(game.game.players[2].name);
                    playerTags[3].setText(game.game.players[3].name);

                    playerTags[2].setBackgroundTintList(getColorStateList(R.color.Pale_Gray));
                    playerTags[3].setBackgroundTintList(getColorStateList(R.color.Pale_Gray));
                }else {
                    playerTags[2].setVisibility(View.GONE);
                    playerTags[3].setVisibility(View.GONE);
                }

                playerTags[game.game.currentPlayer].setBackgroundTintList(getColorStateList(R.color.Light_Blue));

                if(game.game.Get_Game_State() != 0){
                    displayGameOver();
                }


            }
        });

        boardUI.boardClickListener = new BoardOnClick() {
            @Override
            public void clickWall(int x, int y, int dir) {
                System.out.println("Clicked on a wall: (" + x + ", " + y + ")");
                game.Place_Wall(x, y, dir);
            }

            @Override
            public void clickTile(int x, int y) {
                System.out.println("Clicked on a tile: (" + x + ", " + y + ")");
                game.Move_Player(x, y);
            }
        };
    }

    private void displayGameOver(){
        ((TextView) findViewById(R.id.winnerText2)).setText(game.game.players[game.game.Get_Game_State()].name + " Won!");

        findViewById(R.id.returnButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });


        //this double thing shouldn't be necessary but fuck android for being super buggy (This took me 4 hours to solve)
        //-Carter Wuz Here
        findViewById(R.id.winnerCard2).setVisibility(View.INVISIBLE);
        findViewById(R.id.winnerCard2).setVisibility(View.VISIBLE);

        findViewById(R.id.winnerCard2).setVisibility(View.INVISIBLE);
        findViewById(R.id.winnerCard2).setVisibility(View.VISIBLE);

        playerTags[game.game.Get_Game_State()].setText(playerTags[game.game.Get_Game_State()].getText() + "\uD83D\uDC51");
        playerTags[game.game.Get_Game_State()].setBackgroundTintList(getColorStateList(R.color.Dark_Peach));


    }
}