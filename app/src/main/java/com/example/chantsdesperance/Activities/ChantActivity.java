package com.example.chantsdesperance.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chantsdesperance.Models.Chants;
import com.example.chantsdesperance.R;

import org.parceler.Parcels;

import java.util.Objects;

public class ChantActivity extends AppCompatActivity implements View.OnTouchListener {

    Chants chants;
    TextView tvTexteChant;
    private float textSize;
    private VelocityTracker velocityTracker = null;


    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 5.0f;

    private float scaleFactor = 1.0f;
    private ScaleGestureDetector detector;

    private float lastX, lastY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chant);

        View rootLayout = findViewById(R.id.root_layout);
        rootLayout.setOnTouchListener(this);


        chants = Parcels.unwrap(getIntent().getParcelableExtra("chants"));

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.chant_menu);

        String text = (chants.getnumeroChant()) + " - " ;



        getSupportActionBar().setTitle(text + chants.gettitreChant());


        tvTexteChant = findViewById(R.id.tvTexteChant);
        tvTexteChant.setText(chants.gettexteChant());

        // Set the touch listener on the view

        tvTexteChant.setOnTouchListener(this);


        // Set up the scale gesture detector
        detector = new ScaleGestureDetector(this, new ScaleListener());



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
        getMenuInflater().inflate(R.menu.chant_menu, menu);
        return true;
    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Pass the event to the scale gesture detector
        detector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Start tracking velocity
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                velocityTracker.addMovement(event);
                lastX = event.getX();
                lastY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                // Track velocity and update text position
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                float deltaX = event.getX() - lastX;
                float deltaY = event.getY() - lastY;
                lastX = event.getX();
                lastY = event.getY();
                float newX = tvTexteChant.getX() + deltaX / scaleFactor +
                        velocityTracker.getXVelocity() / scaleFactor / 1000;
                float newY = tvTexteChant.getY() + deltaY / scaleFactor +
                        velocityTracker.getYVelocity() / scaleFactor / 1000;
                tvTexteChant.setX(newX);
                tvTexteChant.setY(newY);
                break;

            case MotionEvent.ACTION_UP:
                // Stop tracking velocity
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }

        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // Get the scale factor
            scaleFactor *= detector.getScaleFactor();

            // Make sure the scale factor is within the min/max limits
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));

            // Apply the scale factor to the view
            tvTexteChant.setScaleX(scaleFactor);
            tvTexteChant.setScaleY(scaleFactor);

            return true;
        }
    }


}
