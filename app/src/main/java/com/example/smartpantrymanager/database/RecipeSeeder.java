package com.example.smartpantrymanager.database;

import java.util.Arrays;
import java.util.List;

public class RecipeSeeder {

    public static void seed(PantryDatabase database) {

        List<Recipe> recipes = Arrays.asList(

                new Recipe(
                        "Chicken Rice",
                        "chicken,rice,onion,tomato",
                        "Cook the rice. Cook the chicken with onion and tomato. Serve together."
                ),

                new Recipe(
                        "Tomato Pasta",
                        "pasta,tomato,onion,garlic",
                        "Cook the pasta. Prepare a tomato, onion and garlic sauce. Mix with the pasta."
                ),

                new Recipe(
                        "Chicken Sandwich",
                        "chicken,bread,lettuce,tomato",
                        "Cook the chicken. Place chicken, lettuce and tomato between slices of bread."
                ),

                new Recipe(
                        "Egg Fried Rice",
                        "rice,egg,onion,carrot",
                        "Cook the rice. Fry onion and carrot, then add egg and rice. Stir together."
                ),

                new Recipe(
                        "Vegetable Omelette",
                        "egg,tomato,onion,pepper",
                        "Beat the eggs. Fry the vegetables, add the eggs and cook until set."
                ),

                new Recipe(
                        "Tuna Sandwich",
                        "tuna,bread,mayonnaise,lettuce",
                        "Mix tuna with mayonnaise. Add lettuce and place between slices of bread."
                ),

                new Recipe(
                        "Chicken Wrap",
                        "chicken,tortilla,lettuce,tomato",
                        "Cook the chicken. Add chicken, lettuce and tomato to a tortilla and wrap."
                ),

                new Recipe(
                        "Beef Pasta",
                        "beef,pasta,tomato,onion",
                        "Cook the pasta. Cook the beef with onion and tomato. Combine with pasta."
                ),

                new Recipe(
                        "Tomato Rice",
                        "rice,tomato,onion,garlic",
                        "Cook onion and garlic. Add tomato and rice, then cook until the rice is ready."
                ),

                new Recipe(
                        "Chicken Salad",
                        "chicken,lettuce,tomato,cucumber",
                        "Cook the chicken. Chop the vegetables and combine everything in a bowl."
                ),

                new Recipe(
                        "Cheese Omelette",
                        "egg,cheese,milk",
                        "Beat the eggs with milk. Cook in a pan and add cheese before folding."
                ),

                new Recipe(
                        "Peanut Butter Sandwich",
                        "bread,peanut butter,banana",
                        "Spread peanut butter on bread and add sliced banana."
                ),

                new Recipe(
                        "Pancakes",
                        "flour,egg,milk,sugar",
                        "Mix the ingredients into a batter. Cook small portions in a pan until golden."
                ),

                new Recipe(
                        "French Toast",
                        "bread,egg,milk,sugar",
                        "Dip bread in the egg and milk mixture. Fry both sides until golden."
                ),

                new Recipe(
                        "Chicken Soup",
                        "chicken,carrot,onion,potato",
                        "Cook chicken with onion. Add carrot and potato with water and simmer until tender."
                ),

                new Recipe(
                        "Beef Stew",
                        "beef,potato,carrot,onion",
                        "Brown the beef. Add onion, potato and carrot with water and simmer until tender."
                ),

                new Recipe(
                        "Vegetable Rice",
                        "rice,carrot,peas,corn",
                        "Cook the rice. Fry the vegetables and mix them with the cooked rice."
                ),

                new Recipe(
                        "Macaroni Cheese",
                        "macaroni,cheese,milk,butter",
                        "Cook the macaroni. Make a cheese sauce using milk and butter, then mix together."
                ),

                new Recipe(
                        "Banana Smoothie",
                        "banana,milk,yogurt,honey",
                        "Blend banana, milk, yogurt and honey until smooth."
                ),

                new Recipe(
                        "Chicken and Potato",
                        "chicken,potato,onion,garlic",
                        "Cook the chicken with onion and garlic. Add potatoes and cook until tender."
                )
        );

        for (Recipe recipe : recipes) {
            database.recipeDao().insert(recipe);
        }
    }
}