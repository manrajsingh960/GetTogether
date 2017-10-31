/*
    This page allows the user to create an account for the GetTogether app.
    It is accessed from the 'Login' page.

*/
package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_form);
    }

    public void goToLogin(View view){

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
