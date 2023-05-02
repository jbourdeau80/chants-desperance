package com.example.chantsdesperance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.parceler.Parcels;

public class ChantActivity extends AppCompatActivity {

    Chants chants;
    TextView tvTexteChant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chant);

        chants = Parcels.unwrap(getIntent().getParcelableExtra("chants"));
        tvTexteChant = findViewById(R.id.tvTexteChant);
        tvTexteChant.setText(chants.gettexteChant());



    }
}