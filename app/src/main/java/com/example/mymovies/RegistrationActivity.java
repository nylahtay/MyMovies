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

public class RegistrationActivity extends AppCompatActivity {

    private EditText etRegUser, etRegPass, etRegPassVerify;
    private Button buttonRegister;

    public static Credentials credentials;

    //add variables for Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;


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

                //validate the inputs
                if(validate(regUsername, regPassword, regPassVerify))
                {
                    //create the new credentials
                    credentials = new Credentials(regUsername, regPassword);
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

                    //Store the credentials in Shared Preferences
                    sharedPreferencesEditor.putString("Username", regUsername);
                    sharedPreferencesEditor.putString("Password", regPassword);

                    sharedPreferencesEditor.apply();

                    //Toast for successful Registration
                    Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();

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
}