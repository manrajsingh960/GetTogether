package com.example.manrajsingh960.gettogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    public enum Process{
        PRINT,
        JOIN
    }

    private TextView tvTitle;
    private TextView tvDescription;
    private int joinId;
    private String joinUser;

    private Button btJoinEvent;
    private TextView tvError;

    private ProgressDialog printProgress;
    private ProgressDialog joinProgress;

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
        //tvCreator = (TextView) findViewById(R.id.viewCreator);
        btJoinEvent =(Button) findViewById((R.id.joinEventButton));
        tvError = (TextView) findViewById(R.id.doesNotExistJoinEvent);

        setId();
        setJoinUser();

        displayDialogForPrint();
        process = Process.PRINT;
        checkEventExistence();
    }

    public void displayDialogForPrint(){
        printProgress = new ProgressDialog(JoinEvent.this);
        printProgress.setTitle("Displaying Event Info");
        printProgress.setMessage("Waiting for response from internet...");
        printProgress.setCancelable(false);
        printProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });
        printProgress.show();
    }

    public void displayDialogForJoin(){
        joinProgress = new ProgressDialog(JoinEvent.this);
        joinProgress.setTitle("Joining Event");
        joinProgress.setMessage("Waiting for response from internet...");
        joinProgress.setCancelable(false);
        joinProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                joinProgress.dismiss();
            }
        });
        joinProgress.show();
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
                                printProgress.setMessage("Displaying info...");
                                printInfo();
                            } else {
                                printProgress.setMessage("Cannot find event...");
                                doesNotExistError();
                            }

                            printProgress.dismiss();

                            break;

                        case JOIN:

                            if (success) {
                                joinProgress.setMessage("Found event in database...");
                                putEventInDatabase();
                            } else {
                                joinProgress.dismiss();
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
        int count = sharedPref.getInt("count", 0);

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
        displayDialogForJoin();
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
                        joinProgress.setMessage("Joining...");
                        toastMessage.makeMessage("You have joined this event");
                        Intent intent = new Intent(JoinEvent.this, MainMenu.class);
                        startActivity(intent);
                    } else {
                        joinProgress.dismiss();
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

        joinProgress.setMessage("Attempting to join...");

        JoinEventRequest joinEventRequest = new JoinEventRequest(joinUser, joinId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JoinEvent.this);
        queue.add(joinEventRequest);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyEvents.class);
        startActivity(intent);
    }
}