package com.ucsm.paisesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ucsm.paisesapp.R;
import com.ucsm.paisesapp.adapter.CountryAdapter;
import com.ucsm.paisesapp.api.CountryApi;
import com.ucsm.paisesapp.api.RetrofitClient;
import com.ucsm.paisesapp.database.AppDatabase;
import com.ucsm.paisesapp.database.CountryEntity;
import com.ucsm.paisesapp.database.DatabaseClient;
import com.ucsm.paisesapp.model.Country;
import com.ucsm.paisesapp.model.CountryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountryAdapter adapter;

    private List<CountryEntity> list;
    private List<CountryEntity> allCountries;

    private AppDatabase db;
    private CountryApi api;

    private Button btnFavorites;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerCountries);
        btnFavorites = findViewById(R.id.btnFavorites);
        searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = DatabaseClient.getInstance(this);
        api = RetrofitClient.getClient().create(CountryApi.class);

        list = new ArrayList<>();
        allCountries = new ArrayList<>();

        adapter = new CountryAdapter(this, list);
        recyclerView.setAdapter(adapter);

        btnFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterCountries(newText);
                        return true;
                    }
                });

        loadFromRoom();

        if (allCountries.isEmpty()) {
            loadFromApi();
        }
    }

    private void loadFromApi() {

        api.getCountries().enqueue(new Callback<CountryResponse>() {

            @Override
            public void onResponse(Call<CountryResponse> call,
                                   Response<CountryResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Country> countries = response.body().data;

                    List<CountryEntity> entities = new ArrayList<>();

                    for (Country c : countries) {

                        CountryEntity e = new CountryEntity();
                        e.country = c.country;

                        if (c.cities != null) {
                            e.cities = String.join(",", c.cities);
                        } else {
                            e.cities = "";
                        }

                        entities.add(e);
                    }

                    db.countryDao().deleteAll();
                    db.countryDao().insertAll(entities);

                    Log.d("API", "Datos guardados en Room");

                    loadFromRoom();
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    private void loadFromRoom() {

        List<CountryEntity> data = db.countryDao().getAll();

        allCountries.clear();
        allCountries.addAll(data);

        list.clear();
        list.addAll(data);

        adapter.notifyDataSetChanged();

        Log.d("ROOM", "Países cargados: " + data.size());
    }

    private void filterCountries(String text) {

        if (text == null || text.trim().isEmpty()) {

            adapter.updateList(new ArrayList<>(allCountries));
            return;
        }

        List<CountryEntity> filtered = new ArrayList<>();

        for (CountryEntity country : allCountries) {

            if (country.country != null &&
                    country.country.toLowerCase()
                            .contains(text.toLowerCase())) {

                filtered.add(country);
            }
        }

        adapter.updateList(filtered);
    }
}