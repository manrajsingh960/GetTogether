/*
    The main menu for the app, where users can choose to either join and event, or create an event
    This page can be accessed from 'Login' after a user successfully logs in.

*/
package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void goToCreateEventForm(View view){

        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }

    public void goToJoinMenu(View view){

        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }
}
