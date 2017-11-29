package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JoinEvent extends AppCompatActivity {
    /* Dennis
    private Button btnJoin;
    private TextView txtView, txtView2;
    DatabaseHelper mDbHelper;
    private int row;
    */

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCreator;
    private String joinName;
    private String joinTitle;
    private String joinDescription;
    private String joinCreator;
    private int joinEventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_event);

        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "eventInfo" + row;

        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        int id = sharedPref.getInt("id", 0);
        String title = sharedPref.getString("title","");
        String description = sharedPref.getString("description", "");
        String creator = sharedPref.getString("creator", "");

        tvTitle = (TextView) findViewById(R.id.viewTitle);
        tvTitle.setText(title);
        tvDescription = (TextView) findViewById(R.id.viewDescription);
        //tvDescription.setText(description);
        tvCreator = (TextView) findViewById(R.id.viewCreator);
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

        joinEventId = id;
        joinTitle = title;
        joinDescription = description;
        joinCreator = creator;

        /* Dennis

        txtView = (TextView)findViewById(R.id.textView);
        txtView2 = (TextView)findViewById(R.id.textView2);
        mDbHelper = new DatabaseHelper(this);
        Intent receivedIntent = getIntent();
        row = receivedIntent.getIntExtra("row", 0);

        /*Cursor data = mDbHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        for(int j=-1;j<row;j++){
            data.moveToNext();
            if(j==row-1){
                listData.add(data.getString(1)); //0 Title
                listData.add(data.getString(2)); //1 Start time
                listData.add(data.getString(3)); //2 End time
                listData.add(data.getString(4)); //3 Location
                listData.add(data.getString(5)); //4 Description
            }
        }

        txtView.setText(listData.get(0));
        txtView2.setText(listData.get(4) + "\n\n\nLOCATION: " + listData.get(3) + "\n\nSTARTS AT: " + listData.get(1) + "\n\nENDS AT: " + listData.get(2));*/
    }

    public void saveJoinData(int row){

        String name = "joinInfo" + row;
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", joinEventId);
        editor.putString("title", joinTitle);
        editor.putString("description", joinDescription);
        editor.putString("creator", joinCreator);
        //editor.putBoolean("initialized", true);
        editor.apply();
    }

    public void joiningEvent(View view){
        Intent intent = new Intent(this, MainMenu.class);
        Toast.makeText(this, "Joining event...", Toast.LENGTH_SHORT).show();

        /*
        Then content inside the if statement will save the event that the user clicked.
        The saveID() will save the id of the event that the user is joining so it can be checked
        every time we join the same event and not save it since it should already have been saved.
        */

        if ( !(alreadyJoined()) ) {

            int row = getIncrement();

            saveJoinData(row);
            saveID(row);
            row += 1;
            setIncrement(row);
        }

        startActivity(intent);
    }

    /* We are taking data from the eventInfo Shared Pref local database and we are putting into a joinInfo
    Shared Pref local database so it will be easy to display the events a user has joined furthur on.
    We are also saving the index of the the name of the file so we can increment it ONLY when the user wants to join
    another ever so when we retrieve this information when we display the list view in the JoinedEvents class we can
    have a index to go through the multiple number of events the user may have joined.
     */

    private void setIncrement(int row){
        SharedPreferences sharedPref = getSharedPreferences("increment", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("row", row);
        editor.apply();
    }

    private int getIncrement(){
        SharedPreferences sharedPref = getSharedPreferences("increment", Context.MODE_PRIVATE);
        int row = sharedPref.getInt("row", 0);
        return  row;
    }

    private void saveID(int row){
        String name = "joinID" + row;
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", joinEventId);
        editor.apply();
    }

    private boolean alreadyJoined(){
        int total = getIncrement();
        boolean alreadyJoined = false;
        String name;
        for (int i = 0; i < total; i++){
            name  = "joinID" + i;
            SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
            if ( joinEventId == sharedPref.getInt("id", -1) )
                alreadyJoined = true;
        }
        return alreadyJoined;
    }

    public void goBack(View view){
        SharedPreferences sharedPref = getSharedPreferences("refresh1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("creating", true);
        editor.apply();

        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

}