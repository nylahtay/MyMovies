package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistrationActivity extends AppCompatActivity {

    private EditText etRegUser, etRegPass, etRegPassVerify;
    private Button buttonRegister;

    public static Credentials credentials;
    public static User user;

    //add variables for Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    boolean isValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etRegUser = findViewById(R.id.editTextRegistration_EmailAddress);
        etRegPass = findViewById(R.id.editTextRegistration_Password);
        etRegPassVerify = findViewById(R.id.editTextRegitration_PasswordVerification);
        buttonRegister = findViewById(R.id.buttonRegistration_Register);

        //Get the Shared Preferences file for CredentialsDB.
        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();


        //If the Register button is clicked
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //grab inputs as strings
                String regUsername = etRegUser.getText().toString();
                String regPassword = etRegPass.getText().toString();
                String regPassVerify = etRegPassVerify.getText().toString();
                String regFirst = "";
                String regLast = "";


                //validate the inputs
                if(validate(regUsername, regPassword, regPassVerify))
                {
                    //create the new credentials
                    credentials = new Credentials(regUsername, regPassword);
                    user = new User(credentials, regFirst, regLast, "0");

                    //Store the credentials in Shared Preferences
                    sharedPreferencesEditor.putString("Username", regUsername);
                    sharedPreferencesEditor.putString("Password", regPassword);
                    sharedPreferencesEditor.putString("FirstName", regFirst);
                    sharedPreferencesEditor.putString("LastName", regLast);
                    sharedPreferencesEditor.putString("UserAuth", "0");


                    registerOnline(regUsername, regPassword, regFirst, regLast);



                    sharedPreferencesEditor.apply();

                }

            }
        });
    }

    //This method validates the inputs
    private boolean validate(String username, String password1, String password2){

        //Some quick validation
        //if username is empty OR
        if(username.isEmpty()){
            Toast.makeText(this, "Please enter Username", Toast.LENGTH_LONG).show();
            return false;
        }

        //TODO
        //Check to see if the username already exists
        /*

        -- CODE HERE --

        if(username is in database)
        {
            Toast.makeText(this, "Error: Email already exists.", Toast.LENGTH_LONG).show();
            return false;
        }

        */

        //if password is less than 8 characters
        if(password1.length() < 8 || password2.length() < 8)
        {
            Toast.makeText(this, "Password must be 8 characters long", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!password1.equals(password2))
        {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    };


    private void registerOnline(String username, String password, String first, String last){
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("username", username);
            object.put("password_hash", password);
            object.put("first", first);
            object.put("last", last);
            object.put("auth", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //todo add url to strings
        // Enter the correct url for your api service site
        //String url = getResources().getString(R.string.url);
        String url = "http://mymovies.nylahtay.com/api/create_users.php";

        //Create the Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String stringResponse = "String Response : "+ response.toString();
                        Toast.makeText(RegistrationActivity.this, stringResponse, Toast.LENGTH_LONG).show();

                        //create a string to hold the returned sql error if user is not added to db table
                        String userCreateError = "";
                        try {
                            isValid = response.getBoolean("user_created");
                            userCreateError = response.getString("error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Check to see if registration was a success
                        if (isValid){
                            //Registration successful
                            Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();

                            //go to MainActivity
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, userCreateError, Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String stringResponse = "Error getting response" + error;
                Toast.makeText(RegistrationActivity.this, stringResponse, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}