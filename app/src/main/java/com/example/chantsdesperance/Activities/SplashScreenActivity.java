package com.example.chantsdesperance.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chantsdesperance.R;


// This class represents a splash screen activity that is displayed when the app starts. It shows a splash screen for a fixed duration
// and then transitions to the SectionActivity
public class SplashScreenActivity extends AppCompatActivity {

    // Sets up the activity to display a fullscreen splash screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);


        new Handler().postDelayed(new Runnable() {
            // After a delay of 2000 milliseconds, starts the SectionActivity and finishes the splash screen activity
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, SectionActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}