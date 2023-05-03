package com.example.chantsdesperance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListChantsActivity extends AppCompatActivity {

    List<Chants> chants;
    Section section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chants);

        section = Parcels.unwrap(getIntent().getParcelableExtra("section"));


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(section.getnomSection());




        RecyclerView recyclerView = findViewById(R.id.rvChants);
        chants = new ArrayList<>();

        ListChants_Adapter adapter = new ListChants_Adapter(this, chants);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        try {
            chantsList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void chantsList() throws JSONException, IOException {
        InputStream inputStream = getAssets().open("chantsdesperance.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer, "UTF-8");

        JSONObject jsonObject = new JSONObject(json);

        JSONArray jsonArray = jsonObject.getJSONArray("Chants");

        String nomSection = section.nomSection;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray ChantsArray = jsonArray.getJSONArray(i);
            int indice_section = ChantsArray.getInt(0);
            if (indice_section == section.indiceSection) {
                int numeroChant = ChantsArray.getInt(1);
                String titreChant = ChantsArray.getString(2);
                String texteChant = ChantsArray.getString(3);

                Chants chant = new Chants(numeroChant, titreChant, texteChant, nomSection);
                Log.i("ListChantsActivity", chant.titreChant);
                chants.add(chant);
            }

        }
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Intent intent = new Intent(ListChantsActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        return true;
    }
}
