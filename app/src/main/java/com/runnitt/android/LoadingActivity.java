package com.runnitt.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.runnitt.android.services.AuthService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //check if user is logged in
        new CountDownTimer(2000, 1000){
            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }

            public void onFinish() {
                verifyLoggedIn();
            }
        }.start();
    }

    private void verifyLoggedIn(){
        if(AuthService.isUserLoggedIn(LoadingActivity.this)){
            //if logged in go to dashboard activity
            Intent mainActivityIntent = new Intent(LoadingActivity.this, MapsActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }else {
            //else go to login activity
            Intent loginActivityIntent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(loginActivityIntent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AuthService.isUserLoggedIn(LoadingActivity.this)){
            //if logged in go to dashboard activity
            Intent mainActivityIntent = new Intent(LoadingActivity.this, MapsActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }
        Log.d(this.getClass().getName(), "ON RESUME");
    }
}
