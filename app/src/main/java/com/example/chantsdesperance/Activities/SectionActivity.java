package com.example.chantsdesperance.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chantsdesperance.Adapters.Section_Adapter;
import com.example.chantsdesperance.Models.Section;
import com.example.chantsdesperance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// This class represents an activity that displays a list of sections. It includes functions for initializing the activity,
// populating the section list, handling menu item selections, and managing the app's theme
public class SectionActivity extends AppCompatActivity {
    List<Section> sections;
    SwitchCompat switchCompat;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_section);

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        String themeMode = sharedPreferences.getString("theme_mode", "light");

        if (themeMode.equals("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        // Initializes the activity, sets up the toolbar, and configures the RecyclerView for displaying sections
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chants D'Esperance");


         switchCompat = (SwitchCompat) findViewById(R.id.myswitch);
         switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((SwitchCompat) v).isChecked();
                int currentNightMode = getResources().getConfiguration().uiMode
                        & Configuration.UI_MODE_NIGHT_MASK;

                SharedPreferences sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (checked & (currentNightMode == Configuration.UI_MODE_NIGHT_NO)){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putString("theme_mode", "dark");
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putString("theme_mode", "light");
                }
                editor.apply();
                recreate();
            }
        });


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

    // Reads a JSON file containing a list of sections and adds them to the activity's section list
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

    // Inflates the menu layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handles the selected menu item based on its ID
        //    - "send_feedback": Starts an email intent for sending feedback
        //    - "share": Starts a share intent for sharing the app's Play Store link
        //    - "dark_light": Toggles the app's theme between light and dark modes
        //    - "exit": Finishes the activity and closes the app

        switch (item.getItemId()) {
            case R.id.send_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getString(R.string.developpers_email), null));

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
                startActivity(Intent.createChooser(emailIntent, "Send email"));
                return true;

            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String address =
                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                shareIntent.putExtra(Intent.EXTRA_TEXT, address);
                startActivity(Intent.createChooser(shareIntent, "Share App"));

            case  R.id.about:
                // Show the "About" dialog
                showAboutDialog();
                return true;

            case R.id.exit:
                finishAffinity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAboutDialog() {
        // Create and customize the dialog to display app information
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About Chants D'Esperance App");
        builder.setMessage("Version 1.0\nDeveloped by Jennifer Bourdeau And Mydleyka Dimanche\n© 2023 My App Inc.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}