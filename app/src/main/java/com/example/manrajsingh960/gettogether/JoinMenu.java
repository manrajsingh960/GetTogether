package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JoinMenu extends AppCompatActivity {
    //DatabaseHelper mDbHelper;
    private ListView lvJoinMenu;
    private static final String SAVE_EVENT_REQUEST_URL = "https://manrajsingh960.000webhostapp.com/SaveEvent.php";
    private String [] title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_menu);

        lvJoinMenu = (ListView) findViewById(R.id.joinMenuListView);

        int total = getTotalEvents();
        title = new String[total];

        getEvents();
        getTitles();
        displayList();

        /* Dennis's code
        mListView = (ListView) findViewById(R.id.listView);
        mDbHelper = new DatabaseHelper(this);
        populateListView();
        */
    }

    /* Dennis's code

    private void populateListView() {
        Cursor data = mDbHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent expandIntent = new Intent(JoinMenu.this, JoinEvent.class);
                expandIntent.putExtra("row", i);
                startActivity(expandIntent);
            }
        });
    }

    */

    private void getEvents(){
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

                        saveEventData(id, title, description, startHour, startMin, endHour,
                                endMin, startTimeValue, endTimeValue, creator, i, jsonResponse.length());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(JoinMenu.this);
                    builder1.setMessage(e.getMessage()).create().show();

                }

            }
        };

        StringRequest saveEventRequest = new StringRequest(Request.Method.GET, SAVE_EVENT_REQUEST_URL, responseListener, null);
        RequestQueue queue = Volley.newRequestQueue(JoinMenu.this);
        queue.add(saveEventRequest);
    }

    private void saveEventData(int id, String title, String description, int startHour, int startMin, int endHour,
                               int endMin, String startTimeValue, String endTimeValue, String creator,
                               int index, int totalEvents){
        String name = "eventInfo" + index;
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", id);
        editor.putString("title", title);
        editor.putString("description", description);
        editor.putInt("startHour", startHour);
        editor.putInt("startMin", startMin);
        editor.putInt("endHour", endHour);
        editor.putInt("endMin", endMin);
        editor.putString("startTimeValue", startTimeValue);
        editor.putString("endTimeValue", endTimeValue);
        editor.putString("creator", creator);
        editor.putInt("totalEvents", totalEvents);
        editor.apply();
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
        lvJoinMenu.setAdapter(arrayAdapter);
    }

    private int getTotalEvents(){
        SharedPreferences total = getSharedPreferences("eventInfo0", Context.MODE_PRIVATE);
        int totalEvents = total.getInt("totalEvents", 0);
        return totalEvents;
    }

    public void goToMap(View view){

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}