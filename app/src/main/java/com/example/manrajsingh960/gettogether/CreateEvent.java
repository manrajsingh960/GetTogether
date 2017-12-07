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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateEvent extends AppCompatActivity {

    private EditText etTitle, etStartHour, etStartMin, etEndHour, etEndMin, etDescription, etLoc;
    private ToggleButton tbStartTimeIsPM, tbEndTimeIsPM;
    private boolean startTimeIsPM, endTimeIsPM;
    String creator;
    private final ToastMessage toastMessage = new ToastMessage(CreateEvent.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_form);

        etTitle = (EditText) findViewById(R.id.eventTitle);
        etStartHour = (EditText) findViewById(R.id.startTimeHour);
        etStartMin = (EditText) findViewById(R.id.startTimeMin);
        etEndHour = (EditText) findViewById(R.id.endTimeHour);
        etEndMin = (EditText) findViewById(R.id.endTimeMin);
        tbStartTimeIsPM = (ToggleButton) findViewById(R.id.toggleStartTime);
        tbEndTimeIsPM = (ToggleButton) findViewById(R.id.toggleEndTime);
        etDescription = (EditText) findViewById(R.id.eventDescription);
        etLoc = (EditText) findViewById(R.id.eventLoc);

        setCreator();

        tbStartTimeIsPM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    startTimeIsPM = true;
                else
                    startTimeIsPM = false;
            }
        });

        tbEndTimeIsPM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    endTimeIsPM = true;
                else
                    endTimeIsPM = false;
            }
        });
    }

    public void createEvent(View view){

        final String title = etTitle.getText().toString();
        final String description = etDescription.getText().toString();
        final String location  = etLoc.getText().toString();
        //startMin and endMin are String and not int because we want to store the whole number
        //for example we want '01' to store as '01' and not as '1'
        //the only way to do that is to use these vars as type Strings
        final String startMin = etStartMin.getText().toString();
        final String endMin = etEndMin.getText().toString();

        //You cannot have a null string in the Integer.parseInt() paramter.
        //So this if statement does a pre-check on the inputs that are going to be passed--
        //in the Integer.parseInt() method.

        if (etStartHour.getText().toString().length() != 0 && etStartMin.getText().toString().length() != 0
            && etEndHour.getText().toString().length() != 0 && etEndMin.getText().toString().length() != 0) {

            //This if statment will make the user enters two digits in to the minute category
            //This is will storing the correct value eaiser

            if (startMin.length() == 2 && endMin.length() == 2) {

                final int startHour = Integer.parseInt(etStartHour.getText().toString());
                final int endHour = Integer.parseInt(etEndHour.getText().toString());

                String startTimeValue = "AM", endTimeValue = "AM";

                //Error handling: This makes sure all fields that go in the database--
                //have values in them.

                if (title.length() != 0 && description.length() != 0 && location.length() != 0) {

                    //Error handling: This makes sure the time values that go in the database--
                    //are accurate.

                    if (startHour <= 12 && endHour <= 12 && Integer.parseInt(startMin) <= 59 && Integer.parseInt(endMin) <= 59) {

                        if (time(endHour, (Integer.parseInt(endMin)), endTimeIsPM)
                                > time(startHour, (Integer.parseInt(startMin)), startTimeIsPM)) {

                            final ProgressDialog progressDialog = new ProgressDialog(CreateEvent.this);
                            progressDialog.setTitle("Creating Event");
                            progressDialog.setCancelable(false);
                            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog.dismiss();
                                }
                            });
                            progressDialog.show();
                            toastMessage.makeMessage("Retry if it freezes");

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonResponse = new JSONObject(response);

                                        boolean success = jsonResponse.getBoolean("success");

                                        /* This was just for testing

                                        boolean queryWorked = jsonResponse.getBoolean("queryWorked");

                                        int rowsAdded = jsonResponse.getInt("rowsAdded");

                                        */

                                        if (success) {

                                            /*

                                            if (queryWorked){
                                                toastMessage.makeMessage(rowsAdded + "");
                                            } else
                                                toastMessage.makeMessage("Q");

                                            */

                                            progressDialog.setMessage("Creating event...");

                                            toastMessage.makeMessage("Event created");
                                            Intent intent = new Intent(CreateEvent.this, MainMenu.class);
                                            startActivity(intent);
                                        } else {
                                            progressDialog.dismiss();
                                            toastMessage.makeMessage("ERROR: Event not created");
                                        }

                                    } catch (JSONException e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
                                        builder.setMessage("Error cannot create event").create().show();
                                        e.printStackTrace();
                                    }

                                }
                            };

                            if (startTimeIsPM)
                                startTimeValue = "PM";
                            if (endTimeIsPM)
                                endTimeValue = "PM";

                            CreateEventRequest createEventRequest = new CreateEventRequest(title, description, startHour, startMin,
                                    endHour, endMin, startTimeValue, endTimeValue, creator, location, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(CreateEvent.this);
                            queue.add(createEventRequest);

                            progressDialog.setMessage("Waiting for response from internet...");

                        } else if (time(endHour, (Integer.parseInt(endMin)), endTimeIsPM)
                                == time(startHour, (Integer.parseInt(startMin)), startTimeIsPM)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
                            builder.setMessage("End time is same as the start time")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
                            builder.setMessage("End time is before the start time")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
                        builder.setMessage("Wrong time input")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } else
                    toastMessage.makeMessage("Fill all fields");
            } else
                toastMessage.makeMessage("Enter 2 digits for each minute field");
        } else
            toastMessage.makeMessage("Fill all fields");
    }

    public double time(int hr, int min, boolean isPM){
        double time;
        if (isPM && hr >= 1 && hr <= 11){
            hr += 12;
        }
        double minute = min;
        minute /= 100;
        time = hr + minute;
        return time;
    }

    private void setCreator(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        creator = sharedPref.getString("username", "");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to go back?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CreateEvent.this, MainMenu.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
