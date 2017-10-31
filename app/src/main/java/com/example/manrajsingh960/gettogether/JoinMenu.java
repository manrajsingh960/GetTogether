/*
    Users can join an event using this page, and can search or view the map directly
    This page is accessed from the 'Main Menu' page

/*
package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JoinMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_menu);
    }

    public void goToSearchEvent(View view){

        Intent intent = new Intent(this, SearchEvent.class);
        startActivity(intent);
    }

    public void goToMap(View view){

        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }
}
