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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreatedEventsSingle extends AppCompatActivity {

    public enum Process{
        PRINT,
        DELETE
    }

    private TextView tvTitle;
    private TextView tvDescription;
    private final ToastMessage toastMessage = new ToastMessage(CreatedEventsSingle.this);
    private int id;
    private Button btDelete;
    private TextView tvError;

    private ProgressDialog printProgress;
    private ProgressDialog deleteProgress;

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
        setContentView(R.layout.created_events_single);

        tvTitle = (TextView) findViewById(R.id.ceTitle);
        tvDescription = (TextView) findViewById(R.id.ceDescription);
        btDelete = (Button) findViewById(R.id.deleteCreatedEvent);
        tvError = (TextView) findViewById(R.id.doesNotExistCreatedEvents);

        setId();
        displayDialogForPrint();
        process = Process.PRINT;
        checkEventExistence();
    }

    public void displayDialogForPrint(){
        printProgress = new ProgressDialog(CreatedEventsSingle.this);
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

    public void displayDialogForDelete(){
        deleteProgress = new ProgressDialog(CreatedEventsSingle.this);
        deleteProgress.setTitle("Deleting Event");
        deleteProgress.setMessage("Waiting for response from internet...");
        deleteProgress.setCancelable(false);
        deleteProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProgress.dismiss();
            }
        });
        deleteProgress.show();
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

                        case DELETE:

                            if (success) {
                                deleteProgress.setMessage("Found event in database...");
                                takeEventOut();
                            } else {
                                deleteProgress.dismiss();
                                toastMessage.makeMessage("Event Does Not Exist");
                            }

                            break;

                        default:

                            toastMessage.makeMessage("Process not set in java code");
                            break;
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreatedEventsSingle.this);
                    builder1.setMessage("Error").create().show();
                    e.printStackTrace();
                }
            }
        };

        CheckEventExistenceRequest checkEventExistenceRequest = new CheckEventExistenceRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreatedEventsSingle.this);
        queue.add(checkEventExistenceRequest);
    }

    public void doesNotExistError(){
        String errorMessage = "EVENT\nDOES NOT\nEXIST";
        tvError.setText(errorMessage);

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage.makeMessage("Error");
            }
        });
    }

    public void setId(){
        Intent receivedIntent = getIntent();
        int row = receivedIntent.getIntExtra("row", 0);

        String name = "eventInfo" + row;

        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        id = sharedPref.getInt("id", 0);
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
        int count = sharedPref.getInt("count" , 0);

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
        displayDialogForDelete();
        process = Process.DELETE;
        checkEventExistence();
    }

    public void takeEventOut(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success){
                        deleteProgress.setMessage("Deleting...");
                        toastMessage.makeMessage("You have deleted this event");
                        Intent intent = new Intent(CreatedEventsSingle.this, CreatedEvents.class);
                        startActivity(intent);

                    } else {
                        toastMessage.makeMessage("ERROR: could not delete");
                        Intent intent = new Intent(CreatedEventsSingle.this, CreatedEvents.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreatedEventsSingle.this);
                    builder1.setMessage("Error").create().show();

                    e.printStackTrace();
                }
            }
        };

        deleteProgress.setMessage("Attempting to delete...");

        DeleteEventRequest deleteEventRequest = new DeleteEventRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreatedEventsSingle.this);
        queue.add(deleteEventRequest);
    }

    public void goMainMenu(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, CreatedEvents.class);
        startActivity(intent);
    }

}
