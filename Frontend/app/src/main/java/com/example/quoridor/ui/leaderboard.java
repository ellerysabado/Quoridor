package com.example.quoridor.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quoridor.R;


/**
 * Leaderboard screen. Currently nonfunctional.
 *
 * @author Mazin Bashier
 * */
public class leaderboard extends AppCompatActivity {

    TextView wins;
    TextView losses;
    TextView username;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        wins = findViewById(R.id.wins);
        losses = findViewById(R.id.losses);
        username = findViewById(R.id.name);


    }
}
