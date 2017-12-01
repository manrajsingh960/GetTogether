/*
    This page allows the user to create an account for the GetTogether app.
    It is accessed from the 'Login' page.

*/
package com.example.manrajsingh960.gettogether;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccount extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private final ToastMessage toastMessage = new ToastMessage(CreateAccount.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_form);

        etUsername = (EditText) findViewById(R.id.accountUsername);
        etPassword = (EditText) findViewById(R.id.accountPassword);

    }

    public void createAccount(View view) {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        if (username.length() != 0 && password.length() != 0) {

            toastMessage.makeMessage("Creating account...");

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);

                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Intent intent = new Intent(CreateAccount.this, Login.class);
                            CreateAccount.this.startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                            builder.setMessage("Username Taken")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateAccount.this);
                        builder1.setMessage("Error").create().show();
                        e.printStackTrace();
                    }

                }
            };

            RegisterRequest registerRequest = new RegisterRequest(username, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(CreateAccount.this);
            queue.add(registerRequest);
        } else {
            toastMessage.makeMessage("Please fill all the fields");
        }
    }
}
