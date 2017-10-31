/*
    The login screen for the app
    This is the first screen that users will see when opening the app.
*/

package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void goToMainMenu(View view){

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void goToCreateAccountForm(View view){

        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}
