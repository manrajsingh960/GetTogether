package com.example.manrajsingh960.gettogether;

import android.content.Context;
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

    private EditText etTitle, etStartHour, etStartMin, etEndHour, etEndMin, etDescription;
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

                if (title.length() != 0 && description.length() != 0) {

                    toastMessage.makeMessage("Creating new event...");

                    //Error handling: This makes sure the time values that go in the database--
                    //are accurate.

                    if (startHour <= 12 && endHour <= 12 && Integer.parseInt(startMin) <= 59 && Integer.parseInt(endMin) <= 59) {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject jsonResponse = new JSONObject(response);

                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        Intent intent = new Intent(CreateEvent.this, MainMenu.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
                                    builder.setMessage("Error").create().show();
                                    e.printStackTrace();
                                }

                            }
                        };

                        if (startTimeIsPM)
                            startTimeValue = "PM";
                        if (endTimeIsPM)
                            endTimeValue = "PM";

                        CreateEventRequest createEventRequest = new CreateEventRequest(title, description, startHour, startMin,
                                endHour, endMin, startTimeValue, endTimeValue, creator, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(CreateEvent.this);
                        queue.add(createEventRequest);

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

    private void setCreator(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        creator = sharedPref.getString("username", "");
    }
}
