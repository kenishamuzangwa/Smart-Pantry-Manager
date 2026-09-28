package com.example.smartpantrymanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void insert(Recipe recipe);

    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();

    @Query("SELECT COUNT(*) FROM recipes")
    int getRecipeCount();

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);
}
