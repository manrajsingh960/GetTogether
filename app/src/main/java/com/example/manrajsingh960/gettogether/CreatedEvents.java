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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreatedEvents extends AppCompatActivity {

    private ListView lvCreatedEvents;
    private String [] title;
    private Intent refresherIntent;
    private String username;
    private final ToastMessage toastMessage = new ToastMessage(CreatedEvents.this);
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.created_events);

        refresherIntent = getIntent();
        lvCreatedEvents= (ListView) findViewById(R.id.createdEventsListView);
        username = getUsername();

        getEvents();
    }

    public void createList(){

        int total = getTotalEvents();
        title = new String[total];

        getTitles();

        displayList();
    }

    private void getEvents(){

        progressDialog = new ProgressDialog(CreatedEvents.this);
        progressDialog.setTitle("Displaying Events");
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                startActivity(refresherIntent);
            }
        });
        progressDialog.show();
        //toastMessage.makeMessage("Refresh if it freezes");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    progressDialog.setMessage("Getting events...");

                    JSONArray jsonResponse = new JSONArray(response);

                    if (jsonResponse.length() != 0) {

                        SharedPreferences total = getSharedPreferences("totalEvents1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = total.edit();
                        editor.putInt("totalEvents", jsonResponse.length());
                        editor.apply();

                        for (int i = 0; i < jsonResponse.length(); i++) {

                            JSONObject row = jsonResponse.getJSONObject(i);
                            int id = row.getInt("event_id");
                            String title = row.getString("event_title");
                            String description = row.getString("event_description");
                            int startHour = row.getInt("event_startHour");
                            String startMin = row.getString("event_startMin");
                            int endHour = row.getInt("event_endHour");
                            String endMin = row.getString("event_endMin");
                            String startTimeValue = row.getString("event_startTimeValue");
                            String endTimeValue = row.getString("event_endTimeValue");
                            String creator = row.getString("event_creator");
                            String location = row.getString("event_location");
                            int count = row.getInt("event_joined_count");

                            saveEventData(id, title, description, startHour, startMin, endHour,
                                    endMin, startTimeValue, endTimeValue, creator, location, count, i, jsonResponse.length());
                        }

                        progressDialog.setMessage("Displaying events...");

                        createList();

                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        toastMessage.makeMessage("You don't have any created events");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreatedEvents.this);
                    builder1.setMessage(e.getMessage()).create().show();
                }

            }
        };

        CreatedEventsRequest createdEventsRequest = new CreatedEventsRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreatedEvents.this);
        queue.add(createdEventsRequest);

        progressDialog.setMessage("Waiting for response from internet...");
    }

    private String getUsername(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "");
        return username;
    }

    private void saveEventData(int id, String title, String description, int startHour, String startMin, int endHour,
                               String endMin, String startTimeValue, String endTimeValue, String creator,
                               String location, int count, int index, int totalEvents){
        String name = "eventInfo" + index;
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", id);
        editor.putString("title", title);
        editor.putString("description", description);
        editor.putInt("startHour", startHour);
        editor.putString("startMin", startMin);
        editor.putInt("endHour", endHour);
        editor.putString("endMin", endMin);
        editor.putString("startTimeValue", startTimeValue);
        editor.putString("endTimeValue", endTimeValue);
        editor.putString("creator", creator);
        editor.putString("location" , location);
        editor.putInt("count", count);
        editor.putInt("totalEvents", totalEvents);
        editor.apply();
    }

    private int getTotalEvents(){
        SharedPreferences total = getSharedPreferences("totalEvents1", Context.MODE_PRIVATE);
        int totalEvents = total.getInt("totalEvents", 0);
        return totalEvents;
    }

    private void getTitles(){
        int totalEvents = getTotalEvents();
        String name;

        for (int i = 0; i < totalEvents; i++){
            name = "eventInfo" + i;
            SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
            title[i] = sharedPref.getString("title", "");
        }
    }

    private void displayList(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, title);
        lvCreatedEvents.setAdapter(arrayAdapter);
        lvCreatedEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent expandIntent = new Intent(CreatedEvents.this, CreatedEventsSingle.class);
                expandIntent.putExtra("row", i);
                startActivity(expandIntent);
            }
        });
    }

    public void refresh(View view){
        finish();
        startActivity(refresherIntent);
    }

    public void goToMyEvents(View view){
        Intent intent = new Intent(this, MyEvents.class);
        startActivity(intent);
    }
}
