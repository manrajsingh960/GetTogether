package com.example.manrajsingh960.gettogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private final ToastMessage toastMessage = new ToastMessage(JoinedEventsSingle.this);
    private String joinUser;
    private int id;
    private ProgressDialog leaveProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_events_single);

        tvTitle = (TextView) findViewById(R.id.jeTitle);
        tvDescription = (TextView) findViewById(R.id.jeDescription);
        setJoinUser();
        printInfo();
    }

    public void displayDialogForLeave(){
        leaveProgress = new ProgressDialog(JoinedEventsSingle.this);
        leaveProgress.setTitle("Joining Event");
        leaveProgress.setMessage("Waiting for response from internet...");
        leaveProgress.setCancelable(false);
        leaveProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                leaveProgress.dismiss();
            }
        });
        leaveProgress.show();
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
        int count = sharedPref.getInt("count", 0);
        id = sharedPref.getInt("id", (-1));

        tvTitle.setText(title);

        int startHour = sharedPref.getInt("startHour", 0);
        String startMin = sharedPref.getString("startMin", "");
        int endHour = sharedPref.getInt("endHour", 0);
        String endMin = sharedPref.getString("endMin", "");
        String startTimeVal = sharedPref.getString("startTimeValue", "");
        String endTimeVal = sharedPref.getString("endTimeValue", "");

        description = description + "\n\nLocation: " + location + "\n\nNumber of participants: " + count
                + "\n\nEvent created by: " + creator + "\n\nStart Time: "
                + startHour + ":" + startMin + " " + startTimeVal + "\n\n" + "End Time: " + endHour + ":" + endMin + " " + endTimeVal;

        tvDescription.setText(description);
    }

    public void delete(View view){

        displayDialogForLeave();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success){
                        leaveProgress.setMessage("Leaving...");
                        toastMessage.makeMessage("You have left this event");
                        Intent intent = new Intent(JoinedEventsSingle.this, JoinedEvents.class);
                        startActivity(intent);
                    } else {
                        toastMessage.makeMessage("ERROR: could not leave");
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

        leaveProgress.setMessage("Attempting to leave...");

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
