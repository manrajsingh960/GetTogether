package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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

    public enum Process{
        PRINT,
        JOIN
    }

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCreator;
    private int joinId;
    private String joinUser;

    private Button btJoinEvent;
    private TextView tvError;

    private final ToastMessage toastMessage = new ToastMessage(JoinEvent.this);
    /*
        The process variable will keep track of what if statement needs to be executed
        when the volley response is successful.
        PRINT = upon success, print the event's info on screen
        JOIN = upon success, put the event's info into database
        Before you call the checkEventExistence() method make sure you set the correct process
     */
    private Process process;

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
        setJoinUser();
        process = Process.PRINT;
        checkEventExistence();
    }

    private void checkEventExistence(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");

                    switch (process) {

                        case PRINT:

                            if (success) {
                                printInfo();
                            } else {
                                doesNotExistError();
                            }

                            break;

                        case JOIN:

                            if (success) {
                                toastMessage.makeMessage("Joining event...");
                                putEventInDatabase();
                            } else {
                                toastMessage.makeMessage("Event Does Not Exist");
                            }

                            break;

                        default:

                            toastMessage.makeMessage("Process not set in java code");
                            break;
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinEvent.this);
                    builder1.setMessage("Error").create().show();
                    e.printStackTrace();
                }
            }
        };

        CheckEventExistenceRequest checkEventExistenceRequest = new CheckEventExistenceRequest(joinId, responseListener);
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
        String location = sharedPref.getString("location", "");

        tvTitle.setText(title);
        tvCreator.setText("Event created by: " + creator);

        int startHour = sharedPref.getInt("startHour", 0);
        String startMin = sharedPref.getString("startMin", "");
        int endHour = sharedPref.getInt("endHour", 0);
        String endMin = sharedPref.getString("endMin", "");
        String startTimeVal = sharedPref.getString("startTimeValue", "");
        String endTimeVal = sharedPref.getString("endTimeValue", "");

        description = description + "\nLocation: " + location + "\n\nStart Time: " + startHour + ":" + startMin + " " +
                startTimeVal + "\n\n" + "End Time: " + endHour + ":" + endMin + " " + endTimeVal;

        tvDescription.setText(description);
    }

    public void setId(){
        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "eventInfo" + row;

        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        joinId = sharedPref.getInt("id", 0);
    }

    public void setJoinUser(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        joinUser = sharedPref.getString("username", "");
    }

    public void joiningEvent(View view){
        process = Process.JOIN;
        checkEventExistence();
    }

    public void putEventInDatabase(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");

                    if (success){
                        Intent intent = new Intent(JoinEvent.this, MainMenu.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinEvent.this);
                        builder1.setMessage("Could not join event").create().show();
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinEvent.this);
                    builder1.setMessage("Error: Cannot join event").create().show();
                    e.printStackTrace();
                }
            }
        };

        JoinEventRequest joinEventRequest = new JoinEventRequest(joinUser, joinId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JoinEvent.this);
        queue.add(joinEventRequest);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }
}