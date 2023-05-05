package com.example.chantsdesperance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.parceler.Parcels;
import java.util.Objects;

public class ChantActivity extends AppCompatActivity {

    Chants chants;
    TextView tvTexteChant;
    float textSize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chant);


        chants = Parcels.unwrap(getIntent().getParcelableExtra("chants"));

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.menu);

        String text = (chants.getnumeroChant()) + " - " ;


        getSupportActionBar().setTitle(text + chants.gettitreChant());


        tvTexteChant = findViewById(R.id.tvTexteChant);
        tvTexteChant.setText(chants.gettexteChant());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.zoom_out:
                        textSize = tvTexteChant.getTextSize();
                        tvTexteChant.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize + 10);
                        return true;
                    case R.id.zoom_in:
                        textSize = tvTexteChant.getTextSize();
                        tvTexteChant.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 10);
                        return true;

                    case R.id.share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, chants.gettexteChant());
                        startActivity(Intent.createChooser(shareIntent, "Share text using"));
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Intent intent = new Intent(ChantActivity.this, ListChantsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}