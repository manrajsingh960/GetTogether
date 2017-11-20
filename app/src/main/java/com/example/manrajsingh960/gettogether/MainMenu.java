/*
    The main menu for the app, where users can choose to either join and event, or create an event
    This page can be accessed from 'Login' after a user successfully logs in.

*/
package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {

    //private static final String SAVE_EVENT_REQUEST_URL = "https://manrajsingh960.000webhostapp.com/SaveEvent.php";

    private int userId;
    private String username;
    private TextView tvWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        getUserData();

        tvWelcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        String message = "Welcome, " + username;
        tvWelcomeMessage.setText(message);

        //Needed for onActivityResult method in Login.java
        setResult(0);
    }

    public void goToCreateEventForm(View view){

        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }

    public void goToJoinMenu(View view){

        //AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
        //builder.setMessage("TEST").create().show();
/*
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonResponse = new JSONArray(response);

                    for (int i = 0; i < jsonResponse.length(); i++) {

                        JSONObject row1 = jsonResponse.getJSONObject(i);
                        int id = row1.getInt("event_id");
                        String title = row1.getString("event_title");
                        String description = row1.getString("event_description");
                        int startHour = row1.getInt("event_startHour");
                        int startMin = row1.getInt("event_startMin");
                        int endHour = row1.getInt("event_endHour");
                        int endMin = row1.getInt("event_endMin");
                        String startTimeValue = row1.getString("event_startTimeValue");
                        String endTimeValue = row1.getString("event_endTimeValue");
                        String creator = row1.getString("event_creator");


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainMenu.this);
                    builder1.setMessage(e.getMessage()).create().show();

                }

            }
        };

        StringRequest saveEventRequest = new StringRequest(Request.Method.GET, SAVE_EVENT_REQUEST_URL, responseListener, null);
        RequestQueue queue = Volley.newRequestQueue(MainMenu.this);
        queue.add(saveEventRequest);

        */

        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }

    public void logout(View view){

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void getUserData(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "");
    }

    //This method will force user to double tap back button to successfully exit.

    private long lastClick;
    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastClick < 1000) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainMenu.this,"Press back again to exit", Toast.LENGTH_SHORT).show();
            lastClick = now;
        }
    }

}
