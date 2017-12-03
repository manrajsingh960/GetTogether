package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinEvent extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCreator;
    //The join events attributes to be saved in the database
    private String joinTitle;
    private String joinDescription;
    private String joinCreator;
    private int joinId;
    private int joinStartHour;
    private int joinEndHour;
    private String joinStartMin;
    private String joinEndMin;
    private String joinStartTimeValue;
    private String joinEndTimeValue;

    private Button btJoinEvent;
    private TextView tvError;
    private int id;
    private final ToastMessage toastMessage = new ToastMessage(JoinEvent.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_event);

        tvTitle = (TextView) findViewById(R.id.viewTitle);
        tvDescription = (TextView) findViewById(R.id.viewDescription);
        tvCreator = (TextView) findViewById(R.id.viewCreator);
        btJoinEvent =(Button) findViewById((R.id.joinEventButton));
        tvError = (TextView) findViewById(R.id.doesNotExistJoinEvent);

        setId();
        checkEventExistence();
    }

    private void checkEventExistence(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        //AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinEvent.this);
                        //builder1.setMessage("Event exists").create().show();

                        printInfo();


                    } else {

                        //AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinEvent.this);
                        //builder1.setMessage("This event doesn't exist").create().show();
                        doesNotExistError();

                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinEvent.this);
                    builder1.setMessage("Error").create().show();
                    e.printStackTrace();
                }
            }
        };

        CheckEventExistenceRequest checkEventExistenceRequest = new CheckEventExistenceRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JoinEvent.this);
        queue.add(checkEventExistenceRequest);
    }

    public void doesNotExistError(){
        String errorMessage = "EVENT\nDOES NOT\nEXIST";
        tvError.setText(errorMessage);

        btJoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage.makeMessage("Error");
            }
        });
    }

    public void printInfo(){
        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "eventInfo" + row;

        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        String title = sharedPref.getString("title","");
        String description = sharedPref.getString("description", "");
        String creator = sharedPref.getString("creator", "");



        //Assign join events' attributes here

        joinId = id;
        joinTitle = title;
        joinDescription = description;
        joinCreator = creator;


        tvTitle.setText(title);
        tvCreator.setText("Event created by: " + creator);

        int startHour = sharedPref.getInt("startHour", 0);
        String startMin = sharedPref.getString("startMin", "");
        int endHour = sharedPref.getInt("endHour", 0);
        String endMin = sharedPref.getString("endMin", "");
        String startTimeVal = sharedPref.getString("startTimeValue", "");
        String endTimeVal = sharedPref.getString("endTimeValue", "");

        //Assign join events' time attributes here

        joinStartHour = startHour;
        joinEndHour = endHour;
        joinStartMin = startMin;
        joinEndMin = endMin;
        joinStartTimeValue = startTimeVal;
        joinEndTimeValue = endTimeVal;

        description = description + "\n\nStart Time: " + startHour + ":" + startMin + " " +
                startTimeVal + "\n\n" + "End Time: " + endHour + ":" + endMin + " " + endTimeVal;

        tvDescription.setText(description);
    }

    public void setId(){
        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "eventInfo" + row;

        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        id = sharedPref.getInt("id", 0);
    }
/*
    public void saveJoinData(int row){

        String name = "joinInfo" + row;
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", joinId);
        editor.putString("title", joinTitle);
        editor.putString("description", joinDescription);
        editor.putString("creator", joinCreator);
        editor.apply();
    }
*/
    public void joiningEvent(View view){
        Intent intent = new Intent(this, MainMenu.class);
        toastMessage.makeMessage("Joining event...");



        startActivity(intent);
    }

    /* We are taking data from the eventInfo Shared Pref local database and we are putting into a joinInfo
    Shared Pref local database so it will be easy to display the events a user has joined furthur on.
    We are also saving the index of the the name of the file so we can increment it ONLY when the user wants to join
    another ever so when we retrieve this information when we display the list view in the JoinedEvents class we can
    have a index to go through the multiple number of events the user may have joined.
     */
    /*
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
        editor.putInt("id", joinId);
        editor.apply();
    }

    private boolean alreadyJoined(){
        int total = getIncrement();
        boolean alreadyJoined = false;
        String name;
        for (int i = 0; i < total; i++){
            name  = "joinID" + i;
            SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
            if ( joinId == sharedPref.getInt("id", -1) )
                alreadyJoined = true;
        }
        return alreadyJoined;
    }
    */
    public void goBack(View view){
        SharedPreferences sharedPref = getSharedPreferences("refresh1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("creating", true);
        editor.apply();

        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }
}