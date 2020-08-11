package com.runnitt.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.runnitt.android.services.AuthService;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
    }

    public void onLoginPress(View view){
        //Todo: validate email and password

        btLogin.setEnabled(false);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if(authenticate(email, password)){
//            AuthService.loggedIn(this);
            Intent mapActivity = new Intent(this, MapsActivity.class);
            mapActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mapActivity);
            finish();
        }else{
            btLogin.setEnabled(true);
            Toast.makeText(this, "Login attempt failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSignUpPress(View view){
        Toast.makeText(this, "Sign up button pressed", Toast.LENGTH_SHORT).show();
    }

    public void onForgotPasswordPress(View view){
        Toast.makeText(this, "Forgot text pressed", Toast.LENGTH_SHORT).show();
    }

    private boolean authenticate(String email, String password){
        //validate email and password
        return true;
    }

    private void forgotPassword(){}

    private void signUp(){}
}
