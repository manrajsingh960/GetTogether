package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

public class CreateEvent extends AppCompatActivity {
    private Button btnCreate;
    private EditText getTitle, getStart, getEnd, getDescription;
    private AutoCompleteTextView getLoc;
    DatabaseHelper mDbHelper = new DatabaseHelper(this);
    protected GeoDataClient mGeoDataClient;
    private static final LatLngBounds sacState = new LatLngBounds(new LatLng(38.561956, -121.424203), new LatLng(39.561956, -120.424203));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_form);

        getTitle = (EditText) findViewById(R.id.eventName);
        getStart = (EditText) findViewById(R.id.eventStart);
        getEnd = (EditText) findViewById(R.id.eventEnd);
        getDescription = (EditText) findViewById(R.id.eventDescription);
        getLoc = (AutoCompleteTextView) findViewById(R.id.eventLoc);
        btnCreate = (Button) findViewById(R.id.accountCreateButton);

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

    }

    public void  AddData(String entry1, String entry2, String entry3, String entry4, String entry5) {
        boolean insertData = mDbHelper.addData(entry1, entry2, entry3, entry4, entry5);
        if(insertData) {
            toastMessage("Event Created Successfully");
        }
        else {
            toastMessage("Error: Something went wrong");
        }
    }

    public void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
