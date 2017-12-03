package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JoinedEvents extends AppCompatActivity {

    private ListView lvJoinedEvents;
    private String [] title;
    private Intent refresherIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_events);

        lvJoinedEvents = (ListView) findViewById(R.id.joinedEventsListView);

        int total = getTotalJoined();
        title = new String[total];

        getTitles();
        displayList();

        refresherIntent = getIntent();
    }

    private int getTotalJoined(){
        SharedPreferences sharedPref = getSharedPreferences("increment", Context.MODE_PRIVATE);
        int row = sharedPref.getInt("row", 0);
        return  row;
    }

    private void getTitles(){
        int totalEvents = getTotalJoined();
        String name;

        for (int i = 0; i < totalEvents; i++){
            name = "joinInfo" + i;
            SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
            title[i] = sharedPref.getString("title", "");
        }

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
