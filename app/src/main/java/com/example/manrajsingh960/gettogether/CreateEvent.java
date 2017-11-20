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

/* Dennis's code

import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

*/

public class CreateEvent extends AppCompatActivity {


    private EditText etTitle, etStartHour, etStartMin, etEndHour, etEndMin, etDescription;
    private ToggleButton tbStartTimeIsPM, tbEndTimeIsPM;
    private boolean startTimeIsPM, endTimeIsPM;
    String creator;



    /* Dennis's code
    DatabaseHelper mDbHelper = new DatabaseHelper(this);
    protected GeoDataClient mGeoDataClient;
    private static final LatLngBounds sacState = new LatLngBounds(new LatLng(38.561956, -121.424203), new LatLng(39.561956, -120.424203));
    */

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

        /* Dennis's code
        getLoc = (AutoCompleteTextView) findViewById(R.id.eventLoc);

        PlaceAdapter adapter = new PlaceAdapter(this, android.R.layout.simple_dropdown_item_1line);
        getLoc.setAdapter(adapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entry1 = getTitle.getText().toString();
                String entry2 = getStart.getText().toString();
                String entry3 = getEnd.getText().toString();
                String entry4 = getLoc.getText().toString();
                String entry5 = getDescription.getText().toString();
                if (getTitle.length() != 0 && getStart.length() != 0 && getEnd.length() != 0 && getLoc.length() != 0 && getDescription.length() != 0) {
                    AddData(entry1, entry2, entry3, entry4, entry5);
                    getTitle.setText("");
                    getStart.setText("");
                    getEnd.setText("");
                    getDescription.setText("");
                    getLoc.setText("");
                } else {
                    toastMessage("Please fill all the fields");
                }
            }
        });

        */

    }

    public void createEvent(View view){

        final String title = etTitle.getText().toString();
        final String description = etDescription.getText().toString();

        //You cannot have a null string in the Integer.parseInt() paramter.
        //So this if statement does a pre-check on the inputs that are going to be passed--
        //in the Integer.parseInt() method.

        if (etStartHour.getText().toString().length() != 0 && etStartMin.getText().toString().length() != 0
            && etEndHour.getText().toString().length() != 0 && etEndMin.getText().toString().length() != 0) {

            final int startHour = Integer.parseInt(etStartHour.getText().toString());
            final int startMin = Integer.parseInt(etStartMin.getText().toString());
            final int endHour = Integer.parseInt(etEndHour.getText().toString());
            final int endMin = Integer.parseInt(etEndMin.getText().toString());

            String startTimeValue = "AM", endTimeValue = "AM";

            //Error handling: This makes sure all fields that go in the database--
            //have values in them.

            if (title.length() != 0 && description.length() != 0) {

                toastMessage("Creating new event...");

                //Error handling: This makes sure the time values that go in the database--
                //are accurate.

               if (startHour <= 12 && endHour <= 12 && startMin <= 59 && endMin <= 59) {

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
                   if(endTimeIsPM)
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
                toastMessage("Fill all fields");
        } else
            toastMessage("Fill all fields");
    }

    public void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void setCreator(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        creator = sharedPref.getString("username", "");
    }

    /* Dennis's code

    public void  AddData(String entry1, String entry2, String entry3, String entry4, String entry5) {
        boolean insertData = mDbHelper.addData(entry1, entry2, entry3, entry4, entry5);
        if(insertData) {
            toastMessage("Event Created Successfully");
        }
        else {
            toastMessage("Error: Something went wrong");
        }
    }



    */
}
