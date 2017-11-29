package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyEvents extends AppCompatActivity {

    private TextView tvMessage;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_events);

        getUserData();

        tvMessage = (TextView) findViewById(R.id.myEventMessage);
        String message = "These are your events, " + username;
        tvMessage.setText(message);

    }

    public void goToCreatedEvents(View view){
        Intent intent = new Intent(this, CreatedEvents.class);
        startActivity(intent);
    }

    public void goToJoinedEvents(View view){
        Intent intent = new Intent(this, JoinedEvents.class);
        startActivity(intent);
    }

    public void getUserData(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "");
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
