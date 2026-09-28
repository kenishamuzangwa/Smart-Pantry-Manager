package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.PantryDatabase;
import com.example.smartpantrymanager.database.PantryItem;
import com.example.smartpantrymanager.database.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView listViewRecipes;
    private TextView txtNoRecipes;
    private PantryDatabase database;

    private List<Recipe> suggestedRecipes = new ArrayList<>();
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        listViewRecipes = findViewById(R.id.listViewRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        database = PantryDatabase.getInstance(this);

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        new Thread(() -> {

            List<PantryItem> pantryItems = database.pantryItemDao().getAllItems();

            Map<String, Integer> pantryMap = new HashMap<>();

            for (PantryItem item : pantryItems) {

                String name = item.getName().trim().toLowerCase();

                int quantity = item.getQuantity();

                pantryMap.put(name, quantity);
            }

            List<Recipe> allRecipes = database.recipeDao().getAllRecipes();

            for (Recipe recipe : allRecipes) {

                if (RecipeMatcher.matches(recipe.getIngredients(), pantryMap)) {
                    suggestedRecipes.add(recipe);
                }
            }

            runOnUiThread(() -> {

                recipeAdapter = new RecipeAdapter(
                        SuggestedRecipesActivity.this,
                        suggestedRecipes
                );

                listViewRecipes.setAdapter(recipeAdapter);

                if (suggestedRecipes.isEmpty()) {
                    txtNoRecipes.setVisibility(TextView.VISIBLE);
                } else {
                    txtNoRecipes.setVisibility(TextView.GONE);
                }


                listViewRecipes.setOnItemClickListener((parent, view, position, id) -> {

                    Recipe selectedRecipe = suggestedRecipes.get(position);

                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra("recipeName", selectedRecipe.getName());
                    intent.putExtra("recipeIngredients", selectedRecipe.getIngredients());
                    intent.putExtra("recipeInstructions", selectedRecipe.getInstructions());

                    startActivity(intent);

            });

            });


        }).start();
    }
}