package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_form);
    }

    public void goToMainMenu(View view){

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
