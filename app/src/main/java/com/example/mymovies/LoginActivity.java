package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView la_register;
    Button la_loginButton;
    EditText la_username, la_password;
    private CheckBox la_Remember;



    boolean isValid = false;

    //Create the shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        la_username = findViewById(R.id.editTextUserEmailAddress);
        la_password = findViewById(R.id.editTextUserPassword);
        la_loginButton = findViewById(R.id.buttonLogin_login);
        la_register = findViewById(R.id.textViewLogin_register);
        la_Remember = findViewById(R.id.checkBoxLogin_rememberMe);


        //Get the Shared Preferences file for CredentialsDB.
        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        //Get shared preferences credentials values if they exist
        if(sharedPreferences != null)
        {
            String savedUsername = sharedPreferences.getString("Username", "");
            String savedPassword = sharedPreferences.getString("Password", "");

            //if remember me was checked before, pre-populate the username and password
            if (sharedPreferences.getBoolean("RememberMeCheckbox", false))
            {
                la_username.setText(savedUsername);
                la_password.setText(savedPassword);
                la_Remember.setChecked(true);
            }
        }

        //LOGIN button is clicked
        la_loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String inputUser = la_username.getText().toString();
                String inputPassword = la_password.getText().toString();

                if (inputUser.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please fill out both fields", Toast.LENGTH_LONG).show();
                } else {
                    //validate the credentials
                    validate(inputUser, inputPassword);
                }
            }
        });


        //SIGN UP text is clicked
        la_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }


    private void validate(String username, String password){
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("username",username);
            object.put("password_hash",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //todo add url to strings
        // Enter the correct url for your api service site
        //String url = getResources().getString(R.string.url);
        String url = "http://mymovies.nylahtay.com/api/validate_users.php";

        //Create the Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //output the response to a new User object.
                        JSONObject user_data;

                        try {
                            isValid = response.getBoolean("validate");

                            //get the user data
                            user_data = response.getJSONObject("user_data");
                            //save the userdata to RegistrationActivity instead of sharedPreferences as this is user info.
                            RegistrationActivity.user = new User(
                                    username,
                                    password,
                                    user_data.getString("first"),
                                    user_data.getString("last"),
                                    user_data.getString("auth")
                            );


                            String stringResponse = "String Response | First: " + RegistrationActivity.user.getFirstName() +" Last: "+ RegistrationActivity.user.getLastName();
                            Toast.makeText(LoginActivity.this, stringResponse, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //later we will set this to check for response
                        if (isValid){
                            //Login was successful
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();

                            //save 'Remember Me' in saved preferences if checked.
                            sharedPreferencesEditor.putBoolean("RememberMeCheckbox", la_Remember.isChecked());
                            sharedPreferencesEditor.putString("Username", username);
                            sharedPreferencesEditor.putString("Password", password);
                            sharedPreferencesEditor.apply();

                            //go to MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                                Toast.makeText(LoginActivity.this, "Invalid Credentials, Please try again!", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String stringResponse = "Error getting response" + error;
                Toast.makeText(LoginActivity.this, stringResponse, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}