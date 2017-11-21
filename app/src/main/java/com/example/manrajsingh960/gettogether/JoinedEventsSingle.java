package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JoinedEventsSingle extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_events_single);

        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "joinInfo" + row;
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        String title = sharedPref.getString("title","");
        String description = sharedPref.getString("description", "");
        String creator = sharedPref.getString("creator", "");
        int id = sharedPref.getInt("id", 0);

        tvTitle = (TextView) findViewById(R.id.jeTitle);
        tvTitle.setText(title);
        tvCreator = (TextView) findViewById(R.id.jeCreator);
        tvCreator.setText("Event created by: " + creator);
        tvDescription = (TextView) findViewById(R.id.jeDescription);
        tvDescription.setText(description);

    }

    public void goToJoinedEvents(View view){
        Intent intent = new Intent(this, JoinedEvents.class);
        startActivity(intent);
    }
}
