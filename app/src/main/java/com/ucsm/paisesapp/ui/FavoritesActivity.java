package com.ucsm.paisesapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ucsm.paisesapp.R;
import com.ucsm.paisesapp.adapter.CountryAdapter;
import com.ucsm.paisesapp.database.AppDatabase;
import com.ucsm.paisesapp.database.CountryEntity;
import com.ucsm.paisesapp.database.DatabaseClient;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CountryAdapter adapter;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recyclerFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = DatabaseClient.getInstance(this);

        List<CountryEntity> favorites =
                db.countryDao().getFavorites();

        adapter = new CountryAdapter(this, favorites);
        recyclerView.setAdapter(adapter);
    }
}