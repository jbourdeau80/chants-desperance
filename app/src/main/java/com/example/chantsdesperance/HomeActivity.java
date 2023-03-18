package com.example.chantsdesperance;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        Model[] myListData = new Model[]{
                new Model("Chant D'Esperance Francais", R.drawable.book_open_blank_variant),
                new Model("Chant D'Esperance Creole", R.drawable.book_open_blank_variant),
                new Model("Melodies Joyeuse Francais", R.drawable.book_open_blank_variant),
                new Model("Melodies Joyeuse Creole", R.drawable.book_open_blank_variant),
                new Model("Reveillons-Nous Chretiens", R.drawable.book_open_blank_variant),
                new Model("Reveillons-Nous Francais", R.drawable.book_open_blank_variant),
                new Model("Reveillons-Nous Creole", R.drawable.book_open_blank_variant),
                new Model("La voix du Reveil Francais", R.drawable.book_open_blank_variant),
                new Model("La voix du Reveil Creole", R.drawable.book_open_blank_variant),
                new Model("L'ombre du reveil", R.drawable.book_open_blank_variant),
                new Model("Haiti Chante Avec Radio Lumiere", R.drawable.book_open_blank_variant),
                new Model("Haiti Chante Avec Radio Lumiere Ke-yo", R.drawable.book_open_blank_variant),
                new Model("Gloire a l'agneau", R.drawable.book_open_blank_variant),
                new Model("Echo des elus", R.drawable.book_open_blank_variant),

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        myAdapter adapter = new myAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_feedback:
                Toast.makeText(getApplicationContext(), "Send Feedback", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","your_email@example.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for My App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Developer,\n\nI have some feedback for your app:");
                startActivity(Intent.createChooser(emailIntent, "Send email"));
                return true;

            case R.id.share:
                Toast.makeText(getApplicationContext(), "share app", Toast.LENGTH_SHORT).show();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                return true;

            case R.id.exit:
                Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_LONG).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}