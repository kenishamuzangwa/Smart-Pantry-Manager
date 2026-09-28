package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeInstructions;
    private Button btnBackToRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipeInstructions = findViewById(R.id.txtRecipeInstructions);
        btnBackToRecipes = findViewById(R.id.btnBackToRecipes);

        String recipeName = getIntent().getStringExtra("recipeName");
        String recipeIngredients = getIntent().getStringExtra("recipeIngredients");
        String recipeInstructions = getIntent().getStringExtra("recipeInstructions");

        txtRecipeName.setText(recipeName);
        txtRecipeIngredients.setText(formatIngredients(recipeIngredients));
        txtRecipeInstructions.setText(recipeInstructions);

        btnBackToRecipes.setOnClickListener(v -> finish());
    }

    private String formatIngredients(String ingredients) {

        if (ingredients == null || ingredients.isEmpty()) {
            return "No ingredients available.";
        }

        StringBuilder formattedIngredients = new StringBuilder();

        String[] ingredientList = ingredients.split(",");

        for (String ingredient : ingredientList) {

            String[] parts = ingredient.split(":");

            if (parts.length == 2) {
                formattedIngredients.append("• ")
                        .append(parts[0].trim())
                        .append(" — ")
                        .append(parts[1].trim())
                        .append("\n");
            } else {
                formattedIngredients.append("• ")
                        .append(ingredient.trim())
                        .append("\n");
            }
        }

        return formattedIngredients.toString();
    }
}
