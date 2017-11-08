package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class JoinEvent extends AppCompatActivity {
    private Button btnJoin;
    private TextView txtView, txtView2;
    DatabaseHelper mDbHelper;
    private int row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_event);

        txtView = (TextView)findViewById(R.id.textView);
        txtView2 = (TextView)findViewById(R.id.textView2);
        mDbHelper = new DatabaseHelper(this);
        Intent receivedIntent = getIntent();
        row = receivedIntent.getIntExtra("row", 0);

        Cursor data = mDbHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        for(int j=-1;j<row;j++){
            data.moveToNext();
            if(j==row-1){
                listData.add(data.getString(1)); //0 Title
                listData.add(data.getString(2)); //1 Start time
                listData.add(data.getString(3)); //2 End time
                listData.add(data.getString(4)); //3 Location
                listData.add(data.getString(5)); //4 Description
            }
        }

        txtView.setText(listData.get(0));
        txtView2.setText(listData.get(4) + "\n\n\nLOCATION: " + listData.get(3) + "\n\nSTARTS AT: " + listData.get(1) + "\n\nENDS AT: " + listData.get(2));

    }
}