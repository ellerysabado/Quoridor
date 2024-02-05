package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class swamp extends AppCompatActivity {

    Button leaveSwampButton, togglePartyMode;
    Boolean partyModeActive;

    ImageView background, discoBall;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swamp);

        partyModeActive = false;

        leaveSwampButton = findViewById(R.id.leaveSwamp);
        togglePartyMode = findViewById(R.id.partyButton);

        background = findViewById(R.id.backgroundImage);
        discoBall = findViewById(R.id.discoBall);

        Random rand = new Random(0);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if(partyModeActive){
                    background.setBackgroundColor(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                    if(rand.nextBoolean()){
                        discoBall.setColorFilter(Color.argb(150, 255, 255, 255));
                    }else{
                        discoBall.setColorFilter(Color.argb(0, 255, 255, 255));
                    }

                }
                handler.postDelayed(this, 80); // Set time in milliseconds
            }
        };

        handler.postDelayed(runnable, 80);


        leaveSwampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(swamp.this, MainActivity.class));
            }
        });

        togglePartyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(partyModeActive){
                    //already partying
                    partyModeActive = false;
                    togglePartyMode.setText("Activate Party Mode!");
                    background.setBackgroundColor(Color.parseColor("#5F7A40"));
                    discoBall.setVisibility(View.INVISIBLE);


                }else{
                    //have yet to start the party
                    togglePartyMode.setText("Deactivate Party Mode");
                    discoBall.setVisibility(View.VISIBLE);
                    partyModeActive = true;

                }
            }
        });



    }


}