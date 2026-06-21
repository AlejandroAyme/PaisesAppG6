package com.ucsm.paisesapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ucsm.paisesapp.R;
import com.ucsm.paisesapp.database.AppDatabase;
import com.ucsm.paisesapp.database.CountryEntity;
import com.ucsm.paisesapp.database.DatabaseClient;

public class CountryDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvCities;

    Button btnFavorite;

    AppDatabase db;

    int countryId;
    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvCities = findViewById(R.id.tvCities);
        btnFavorite = findViewById(R.id.btnFavorite);

        db = DatabaseClient.getInstance(this);

        countryId = getIntent().getIntExtra("id", -1);

        CountryEntity countryEntity =
                db.countryDao().getById(countryId)
                ;
        if (countryEntity != null) {

            isFavorite = countryEntity.isFavorite;

            if (isFavorite) {
                btnFavorite.setText("Quitar de favoritos");
            } else {
                btnFavorite.setText("Agregar a favoritos");
            }
        }

        String country = getIntent().getStringExtra("country");
        String cities = getIntent().getStringExtra("cities");

        tvTitle.setText(country);
        tvCities.setText(cities);

        btnFavorite.setOnClickListener(v -> {

            isFavorite = !isFavorite;

            db.countryDao().updateFavorite(
                    countryId,
                    isFavorite
            );

            if (isFavorite) {
                btnFavorite.setText("Quitar de favoritos");
            } else {
                btnFavorite.setText("Agregar a favoritos");
            }
        });
    }
}