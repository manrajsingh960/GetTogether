/*
    The login screen for the app
    This is the first screen that users will see when opening the app.
*/

package com.example.manrajsingh960.gettogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private final ToastMessage toastMessage = new ToastMessage(Login.this);

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

            final ProgressDialog progressDialog = new ProgressDialog(Login.this);
            progressDialog.setTitle("Login");
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressDialog.dismiss();
                }
            });
            progressDialog.show();
            toastMessage.makeMessage("Retry if it freezes");

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success){

                            //toastMessage.makeMessage("Logging in...");
                            progressDialog.setMessage("Logging in...");

                            int user_ID = jsonResponse.getInt("userId");
                            String username = jsonResponse.getString("username");

                            saveUserData(user_ID, username);

                            Intent intent = new Intent(Login.this, MainMenu.class);

                            startActivity(intent);

                        } else {

                            progressDialog.dismiss();

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
            //loginRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
            //        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(loginRequest);

            progressDialog.setMessage("Waiting for response from internet...");

        } else {
            toastMessage.makeMessage("Please fill all the fields");
        }
    }

    public void goToCreateAccountForm(View view){

        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    //Saves the data retreived from the database into a local SharedPrefernces file that can be--
    //accessed in other activities

    public void saveUserData(int user_ID, String username){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("user_ID", user_ID);
        editor.putString("username", username);
        editor.apply();
    }

    //This method will disable back buttton so it doesn't inadvertently log you in

    @Override
    public void onBackPressed() {

    }
}



