/*
    The login screen for the app
    This is the first screen that users will see when opening the app.
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

public class Login extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etUsername = (EditText) findViewById(R.id.inputUsername);
        etPassword = (EditText) findViewById(R.id.inputPassword);

    }

    public void login(View view){

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        if (username.length() != 0 && password.length() != 0) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success){

                            int user_ID = jsonResponse.getInt("userId");
                            String username = jsonResponse.getString("username");

                            Intent intent = new Intent(Login.this, MainMenu.class);
                            intent.putExtra("user_id", user_ID);
                            intent.putExtra("username", username);
                            //This will call onActivityResult method when MainMenu activity exits
                            startActivityForResult(intent, 0);

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setMessage("Username or password incorrect")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Login.this);
                        builder1.setMessage("Error").create().show();

                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(loginRequest);

        } else {
            toastMessage("Please fill all the fields");
        }
    }

    public void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void goToCreateAccountForm(View view){

        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    //This method will be called when the MainMenu activity exits.
    //Then this methods will immediately exit the app.
    //This will ensure that when you press the back button when ur in--
    //the main menu you will exit the app instead of going back to the login screen.

    protected void onActivityResult(int EXIT_APP, int resultCode, Intent data){
        finish();
    }
}



