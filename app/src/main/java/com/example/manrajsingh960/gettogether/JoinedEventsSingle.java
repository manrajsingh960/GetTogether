package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JoinedEventsSingle extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCreator;
    private final ToastMessage toastMessage = new ToastMessage(JoinedEventsSingle.this);
    private int id;
    private Button btLeave;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_events_single);

        tvTitle = (TextView) findViewById(R.id.jeTitle);
        tvDescription = (TextView) findViewById(R.id.jeDescription);
        tvCreator = (TextView) findViewById(R.id.jeCreator);
        //btDelete = (Button) findViewById(R.id.deleteCreatedEvent);
        //tvError = (TextView) findViewById(R.id.doesNotExistCreatedEvents);
        printInfo();
    }

    public void printInfo(){
        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "joinedEventInfo" + row;

        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        String title = sharedPref.getString("title","");
        String description = sharedPref.getString("description", "");
        String creator = sharedPref.getString("creator", "");


        tvTitle.setText(title);


        tvCreator.setText("Event created by: " + creator);

        int startHour = sharedPref.getInt("startHour", 0);
        String startMin = sharedPref.getString("startMin", "");
        int endHour = sharedPref.getInt("endHour", 0);
        String endMin = sharedPref.getString("endMin", "");
        String startTimeVal = sharedPref.getString("startTimeValue", "");
        String endTimeVal = sharedPref.getString("endTimeValue", "");

        description = description + "\n\nStart Time: " + startHour + ":" + startMin + " " +
                startTimeVal + "\n\n" + "End Time: " + endHour + ":" + endMin + " " + endTimeVal;

        tvDescription.setText(description);
    }

    public void delete(View view){
        Intent intent = new Intent(this, JoinedEvents.class);
        startActivity(intent);
    }

    public void goMainMenu(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, JoinedEvents.class);
        startActivity(intent);
    }
}
