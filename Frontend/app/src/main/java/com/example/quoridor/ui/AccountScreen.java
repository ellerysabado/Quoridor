package com.example.quoridor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quoridor.R;


/**
 * Account screen that allows the user to see what their username and uuid is, and logout.
 *
 * @author Carter Awbrey
 * */
public class AccountScreen extends AppCompatActivity {

    private Button logoutButton;
    private TextView idTextView, usernameTextView;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(AccountScreen.this, StartScreen.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        logoutButton = findViewById(R.id.logoutButton);
        idTextView = findViewById(R.id.IDText);
        usernameTextView = findViewById(R.id.usernameText);

        SharedPreferences sp = getSharedPreferences("account", MODE_PRIVATE);


        idTextView.setText("User ID: " + sp.getString("id", new String("")));
        usernameTextView.setText("Username: " + sp.getString("username", new String("")));



        logoutButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor ed = sp.edit();
                        ed.clear();
                        ed.apply();

                        startActivity(new Intent(AccountScreen.this, StartScreen.class));
                    }
                }
        );
    }
}