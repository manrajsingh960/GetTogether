/*
    Allows users to search for certain event
    Accessed from 'Join Menu'

*/

package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_event_form);
    }

    public void goToJoinMenu(View view){

        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }
}
