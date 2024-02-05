package com.example.quoridor.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.quoridor.R;
import com.example.quoridor.data.constants;
import com.example.quoridor.ui.gameHistory.fragment;


/**
 * Game chat. Is currently nonfunctional.
 *
 * @author Mazin Bashier
 * */
public class gameChat extends AppCompatActivity {

    private ImageButton sendButton;
    private EditText message;
    private RelativeLayout chatLayout;
    private Context c;
    private gameChatFragment gameChatFragment;
    private Button gamec;
    private long uuid;
    private ScrollView scrollView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game_chat);


        sendButton = findViewById(R.id.sendImage);
        message = findViewById(R.id.sendMessage);
        chatLayout = findViewById(R.id.sendRecieveMessage);
        gameChatFragment = new gameChatFragment();

        gamec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.sendRecieveMessage, gameChatFragment, "gamechat");
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = message.getText().toString();
                if (!m.isEmpty()) {
                    sendMessage(m);
                    addingMessage(m, true);
                    message.setText("");
                }
            }

        });
    }


    public gameChat(){


        FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
        gameChatFragment = (gameChatFragment) fragmentManager.findFragmentByTag("game_chat");



    }
    private void sendMessage(String m) {
        JSONObject message = new JSONObject();
        try {
            message.put("uuid", uuid);
            message.put("message", m);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,

                constants.server_url + "/gameChat",
                  message,
                  new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("chat", "sent successfully");
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Error: Unable to Connect to Server", Toast.LENGTH_LONG).show();
                    }

                });
    }




        private void addingMessage(String m,boolean isSent){
            RelativeLayout mlay = new RelativeLayout(this);
            RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            //if it is sent by the user then move to the right side otherwise move to the left side
            if (isSent == true) {
                lay.addRule(RelativeLayout.ALIGN_PARENT_END);
             
            } else {
                lay.addRule(RelativeLayout.ALIGN_PARENT_START);


            }
            lay.setMargins(13, 13, 13, 13);
            mlay.setLayoutParams(lay);

            TextView messageT = new TextView(this);
            RelativeLayout.LayoutParams mlayp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            mlayp.setMargins(13, 13, 13, 13);
            messageT.setLayoutParams(mlayp);
            messageT.setText(m);
            mlay.addView(messageT);
            chatLayout.addView(mlay);
            scrollView.fullScroll(View.FOCUS_DOWN);
        }




    }


