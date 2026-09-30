package com.example.smartpantrymanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface  PantryItemDao {

    @Insert
    void insert(PantryItem pantryItem);

    @Query("SELECT * FROM pantry_items")
    List<PantryItem> getAllItems();

    @Update
    void update(PantryItem pantryItem);

    @Delete
    void delete(PantryItem pantryItem);

    @Query("DELETE FROM pantry_items")
    void deleteAll();

}
