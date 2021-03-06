/*
    The main menu for the app, where users can choose to either join and event, or create an event
    This page can be accessed from 'Login' after a user successfully logs in.

*/
package com.example.manrajsingh960.gettogether;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private String username;
    private TextView tvWelcomeMessage;
    private DownloadManager downloadManager;
    private static final String HELP_LINK_URL = "https://gettogetherapp.000webhostapp.com/Help.pdf";
    private static final String MAP_LINK_URL = "https://gettogetherapp.000webhostapp.com/Sacstatemap.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        getUserData();

        tvWelcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        String message = "Welcome, " + username;
        tvWelcomeMessage.setText(message);
    }

    public void goToCreateEventForm(View view){

        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }

    public void goToJoinMenu(View view){

        Intent intent = new Intent(this, JoinMenu.class);
        startActivity(intent);
    }

    public void logout(View view){

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void getUserData(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPref.getString("username", "");
    }

    public void help(View view){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to download the help file?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        download();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void downloadMap(View view){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to download the map?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        map();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void map(){
        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(MAP_LINK_URL);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }

    public void download(){
        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(HELP_LINK_URL);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }

    public void goToMyEvents(View view){
        Intent intent = new Intent(this, MyEvents.class);
        startActivity(intent);
    }

    //This method will disable the back button if its empty

    @Override
    public void onBackPressed() {

    }

}
