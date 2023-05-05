package com.example.chantsdesperance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ListChantsActivity extends AppCompatActivity {

    List<Chants> chants;
    Section section;
    ListChants_Adapter adapter;
    RecyclerView rvChants;
    private boolean isSortedByNumber = true;

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

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        return true;

                    case R.id.action_order:
                        sortChants();
                        return true;
                    default:
                        return false;
                }
            }
        });


        rvChants = findViewById(R.id.rvChants);
        chants = new ArrayList<>();

        adapter = new ListChants_Adapter(this, chants);

        rvChants.setHasFixedSize(true);
        rvChants.setLayoutManager(new LinearLayoutManager(this));
        rvChants.setAdapter(adapter);

        try {
            chantsList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sortChants() {
        if (isSortedByNumber) {
            Collections.sort(chants, new Comparator<Chants>() {
                @Override
                public int compare(Chants c1, Chants c2) {
                    return c1.gettitreChant().compareToIgnoreCase(c2.gettitreChant());
                }
            });
        } else {
            Collections.sort(chants, new Comparator<Chants>() {
                @Override
                public int compare(Chants c1, Chants c2) {
                    return Integer.compare(c1.getnumeroChant(), c2.getnumeroChant());
                }
            });
        }
        adapter.notifyDataSetChanged();
        isSortedByNumber = !isSortedByNumber; // toggle the sort order
    }


    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void searchChants(String query) {

        List<Chants> filteredChants = new ArrayList<>();

        for (Chants chant : chants) {
            if (chant.gettitreChant().toLowerCase().contains(query.toLowerCase()) ||
                    chant.gettexteChant().toLowerCase().contains(query.toLowerCase())) {

                filteredChants.add(chant);
            }
        }

        // add filtered chants through the adapter
        adapter.setChants(filteredChants);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchChantsByNumber(int number) {

        List<Chants> filteredChants = new ArrayList<>();


        for (Chants chant : chants) {
            if (String.valueOf(chant.getnumeroChant()).startsWith(String.valueOf(number))) {

                filteredChants.add(chant);
            }
        }

        // add filtered chants through the adapter
        adapter.setChants(filteredChants);
        adapter.notifyDataSetChanged();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ListChantsActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search by number or text...");
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (isInteger(query)) {
                        // search by chant number
                        int chantNumber = Integer.parseInt(query);
                        searchChantsByNumber(chantNumber);
                    } else {
                        // search by chant title or text
                        searchChants(query);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Do something when the user changes the text in the search bar
                    if (newText.isEmpty()) {
                        adapter.setChants(chants);
                        adapter.notifyDataSetChanged();
                        return true;
                    } else {
                        if (isInteger(newText)) {
                            // search by chant number
                            int chantNumber = Integer.parseInt(newText);
                            searchChantsByNumber(chantNumber);
                        } else {
                            // search by chant title or text
                            searchChants(newText);
                        }
                        return true;
                    }
                }
            });
        }
            return super.onCreateOptionsMenu(menu);
    }
}
