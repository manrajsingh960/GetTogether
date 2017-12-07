package com.example.manrajsingh960.gettogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
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

import java.util.ArrayList;

public class JoinMenu extends AppCompatActivity {

    private ListView lvJoinMenu;
    private static final String SAVE_EVENT_REQUEST_URL = "https://gettogetherapp.000webhostapp.com/SaveEvent.php";
    private String [] title;
    private Intent refresherIntent;
    private final ToastMessage toastMessage = new ToastMessage(JoinMenu.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_menu);

        //toastMessage.makeMessage("OnCreate");

        refresherIntent = getIntent();
        lvJoinMenu = (ListView) findViewById(R.id.joinMenuListView);
        getEvents();
    }

    public void createList(){

        int total = getTotalEvents();
        title = new String[total];

        getTitles();

        displayList();
    }

    private void getEvents(){

        final ProgressDialog progressDialog = new ProgressDialog(JoinMenu.this);
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

                        SharedPreferences total = getSharedPreferences("totalEvents0", Context.MODE_PRIVATE);
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
                                    endMin, startTimeValue, endTimeValue, creator, location, count, i);
                        }

                        progressDialog.setMessage("Displaying events...");

                        createList();

                    } else
                        toastMessage.makeMessage("There are no events at this time");

                    progressDialog.dismiss();

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

        progressDialog.setMessage("Waiting for response from internet...");
    }

    private void saveEventData(int id, String title, String description, int startHour, String startMin, int endHour,
                               String endMin, String startTimeValue, String endTimeValue, String creator, String location,
                               int count, int index){
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_text, title);
        lvJoinMenu.setAdapter(arrayAdapter);
        lvJoinMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent expandIntent = new Intent(JoinMenu.this, JoinEvent.class);
                expandIntent.putExtra("row", i);
                startActivity(expandIntent);
            }
        });
    }

    private int getTotalEvents(){
        SharedPreferences total = getSharedPreferences("totalEvents0", Context.MODE_PRIVATE);
        int totalEvents = total.getInt("totalEvents", 0);
        return totalEvents;
    }

    public void refresh(View view){
        finish();
        startActivity(refresherIntent);
    }

    public void goToMain(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}