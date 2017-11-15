/*
    The main menu for the app, where users can choose to either join and event, or create an event
    This page can be accessed from 'Login' after a user successfully logs in.

*/
package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private int userId;
    private String username;
    private TextView tvWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", -1);
        username = intent.getStringExtra("username");
        tvWelcomeMessage = (TextView) findViewById(R.id.welcomeMessage);

        String message = "Welcome, " + username;
        tvWelcomeMessage.setText(message);

        setResult(0);
    }

    public void goToCreateEventForm(View view){

        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }

    public void goToJoinMenu(View view){

        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }

    public void logout(View view){

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    //This method will force user to double tap back button to successfully exit.

    private long lastClick;
    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastClick < 1000) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainMenu.this,"Press back again to exit", Toast.LENGTH_SHORT).show();
            lastClick = now;
        }
    }

}
