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

            RegistrationActivity.credentials = new Credentials(savedUsername, savedPassword);

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
                    isValid = validate(inputUser, inputPassword);

                    //if credentials are not valid
                    if(!isValid)
                    {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials, Please try again!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Login was successful
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();

                        //save 'Remember Me' in saved preferences if checked.
                        sharedPreferencesEditor.putBoolean("RememberMeCheckbox", la_Remember.isChecked());
                        sharedPreferencesEditor.apply();

                        //go to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
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

    //This method will validate the username and password
    private boolean validate(String name, String password)
    {
        //if the credentials are created
        if(RegistrationActivity.credentials != null){

            //validate the username and password
            if(name.equals(RegistrationActivity.credentials.getUsername()) && password.equals(RegistrationActivity.credentials.getPassword()))
            {
                return true;
            }
        }

        return false;
    }
}