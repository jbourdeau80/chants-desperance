package com.example.chantsdesperance;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<Section> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chants D'Esperance");



        RecyclerView recyclerView = findViewById(R.id.rvSection);
        sections = new ArrayList<>();

        Section_Adapter adapter = new Section_Adapter(this, sections);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        try {
            sectionList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sectionList() throws JSONException, IOException {
        InputStream inputStream = getAssets().open("chantsdesperance.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer, "UTF-8");

        JSONObject jsonObject = new JSONObject(json);

        JSONArray jsonArray = jsonObject.getJSONArray("Sections");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray SectionArray = jsonArray.getJSONArray(i);
            int indiceSection = SectionArray.getInt(0);
            String nomSection = SectionArray.getString(1);

            Section section = new Section(nomSection, indiceSection);
            sections.add(section);
        }

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