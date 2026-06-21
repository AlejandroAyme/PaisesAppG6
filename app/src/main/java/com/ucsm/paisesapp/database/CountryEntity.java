package com.ucsm.paisesapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "countries")
public class CountryEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String country;
    public String cities;

    public boolean isFavorite;
}