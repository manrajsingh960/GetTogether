package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JoinedEvents extends AppCompatActivity {

    private ListView lvJoinedEvents;
    private String [] title;
    private Intent refresherIntent;
    private final ToastMessage toastMessage = new ToastMessage(JoinedEvents.this);
    private String joinUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_events);

        refresherIntent = getIntent();
        lvJoinedEvents = (ListView) findViewById(R.id.joinedEventsListView);
        setJoinUser();
        getEvents();
    }

    private void getEvents(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonResponse = new JSONArray(response);

                    if (jsonResponse.length() != 0) {

                        SharedPreferences total = getSharedPreferences("totalJoinedEvents", Context.MODE_PRIVATE);
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

                            saveEventData(id, title, description, startHour, startMin, endHour,
                                    endMin, startTimeValue, endTimeValue, creator, i);
                        }

                        createList();

                    } else
                        toastMessage.makeMessage("You have not joined any events");

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinedEvents.this);
                    builder1.setMessage(e.getMessage()).create().show();
                }
            }
        };

        SaveJoinedEventRequest saveJoinedEventRequest = new SaveJoinedEventRequest(joinUser, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JoinedEvents.this);
        queue.add(saveJoinedEventRequest);
    }

    private void saveEventData(int id, String title, String description, int startHour, String startMin, int endHour,
                               String endMin, String startTimeValue, String endTimeValue, String creator,
                               int index){
        String name = "joinedEventInfo" + index;
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
        editor.apply();
    }

    public void createList(){

        int total = getTotalJoined();
        title = new String[total];

        getTitles();

        displayList();
    }

    private int getTotalJoined(){
        SharedPreferences sharedPref = getSharedPreferences("totalJoinedEvents", Context.MODE_PRIVATE);
        int total = sharedPref.getInt("totalEvents", 0);
        return  total;
    }

    private void getTitles(){
        int totalEvents = getTotalJoined();
        String name;

        for (int i = 0; i < totalEvents; i++){
            name = "joinedEventInfo" + i;
            SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
            title[i] = sharedPref.getString("title", "");
        }

    }

    public void setJoinUser(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        joinUser = sharedPref.getString("username", "");
    }

    private void displayList(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, title);
        lvJoinedEvents.setAdapter(arrayAdapter);
        lvJoinedEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent expandIntent = new Intent(JoinedEvents.this, JoinedEventsSingle.class);
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
