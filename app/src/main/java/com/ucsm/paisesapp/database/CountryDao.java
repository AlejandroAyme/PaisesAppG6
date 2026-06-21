package com.ucsm.paisesapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert
    void insertAll(List<CountryEntity> list);

    @Query("SELECT * FROM countries")
    List<CountryEntity> getAll();

    @Query("DELETE FROM countries")
    void deleteAll();

    @Query("SELECT * FROM countries WHERE isFavorite = 1")
    List<CountryEntity> getFavorites();

    @Query("UPDATE countries SET isFavorite = :favorite WHERE id = :id")
    void updateFavorite(int id, boolean favorite);

    @Query("SELECT * FROM countries WHERE id = :id")
    CountryEntity getById(int id);
}