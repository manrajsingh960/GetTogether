package com.example.manrajsingh960.gettogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class JoinMenu extends AppCompatActivity {
    DatabaseHelper mDbHelper;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_menu);

        mListView = (ListView) findViewById(R.id.listView);
        mDbHelper = new DatabaseHelper(this);
        populateListView();
    }

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

    public void goToSearchEvent(View view){

        Intent intent = new Intent(this, SearchEvent.class);
        startActivity(intent);
    }

    public void goToMap(View view){

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}