package com.example.smartpantrymanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PantryItem.class, Recipe.class}, version = 2, exportSchema = false)
public abstract class PantryDatabase extends RoomDatabase {

    public abstract PantryItemDao pantryItemDao();

    public abstract RecipeDao recipeDao();

    private static PantryDatabase instance;

    public static synchronized PantryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PantryDatabase.class,
                            "pantry_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }

        new Thread(() -> {

            if (instance.recipeDao().getRecipeCount() == 0) {
                RecipeSeeder.seed(instance);
            }

        }).start();

        return instance;
    }
}