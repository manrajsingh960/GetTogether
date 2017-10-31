package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class JoinEvent extends AppCompatActivity {
    private static final String TAG = "EditDataActivity";
    private Button btnJoin;
    private TextView txtView;
    DatabaseHelper mDbHelper;
    private String selectedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_event);

        txtView = (TextView)findViewById(R.id.textView);
        mDbHelper = new DatabaseHelper(this);
        Intent receivedIntent = getIntent();
        selectedName = receivedIntent.getStringExtra("name");
        txtView.setText(selectedName);
    }
}