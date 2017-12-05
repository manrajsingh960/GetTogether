package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinedEventsSingle extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCreator;
    private final ToastMessage toastMessage = new ToastMessage(JoinedEventsSingle.this);
    private Button btLeave;
    private TextView tvError;
    private String joinUser;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_events_single);

        tvTitle = (TextView) findViewById(R.id.jeTitle);
        tvDescription = (TextView) findViewById(R.id.jeDescription);
        tvCreator = (TextView) findViewById(R.id.jeCreator);
        //btDelete = (Button) findViewById(R.id.deleteCreatedEvent);
        //tvError = (TextView) findViewById(R.id.doesNotExistCreatedEvents);
        setJoinUser();
        printInfo();
    }

    public void setJoinUser(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        joinUser = sharedPref.getString("username", "");
    }

    public void printInfo(){
        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "joinedEventInfo" + row;

        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        String title = sharedPref.getString("title","");
        String description = sharedPref.getString("description", "");
        String creator = sharedPref.getString("creator", "");
        String location = sharedPref.getString("location", "");
        id = sharedPref.getInt("id", (-1));

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

    public void delete(View view){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success){
                        toastMessage.makeMessage("You have left this event");
                        Intent intent = new Intent(JoinedEventsSingle.this, JoinedEvents.class);
                        startActivity(intent);
                    } else {
                        toastMessage.makeMessage("Error");
                        Intent intent = new Intent(JoinedEventsSingle.this, JoinedEvents.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinedEventsSingle.this);
                    builder1.setMessage("Error").create().show();

                    e.printStackTrace();
                }
            }
        };

        UnJoinEventRequest unJoinEventRequest = new UnJoinEventRequest(joinUser, id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JoinedEventsSingle.this);
        queue.add(unJoinEventRequest);
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
