package com.example.quoridor.ui.gameHistory;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.quoridor.R;


/**
 * @author Mazin Bashier
 *
 * */
public class MainGameHistoryUi extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        findViewById(R.id.gameHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(new fragment(),"f");
                transaction.addToBackStack("f");
                transaction.commit();
            }
        });
    }
}

