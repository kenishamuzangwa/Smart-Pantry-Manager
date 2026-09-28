package com.example.smartpantrymanager.database;

import java.util.Arrays;
import java.util.List;

public class RecipeSeeder {

    public static void seed(PantryDatabase database) {

        List<Recipe> recipes = Arrays.asList(

                new Recipe(
                        "Chicken Rice",
                        "chicken:2,rice:1,onion:1,tomato:2",
                        "Cook the rice. Cook the chicken with onion and tomato. Serve together."
                ),

                new Recipe(
                        "Tomato Pasta",
                        "pasta:1,tomato:2,onion:1,garlic:1",
                        "Cook the pasta. Prepare a tomato, onion and garlic sauce. Mix with the pasta."
                ),

                new Recipe(
                        "Chicken Sandwich",
                        "chicken:2,bread:2,lettuce:1,tomato:1",
                        "Cook the chicken. Place chicken, lettuce and tomato between slices of bread."
                ),

                new Recipe(
                        "Egg Fried Rice",
                        "rice:1,egg:2,onion:1,carrot:1",
                        "Cook the rice. Fry onion and carrot, then add egg and rice. Stir together."
                ),

                new Recipe(
                        "Vegetable Omelette",
                        "egg:2,tomato:1,onion:1,pepper:1",
                        "Beat the eggs. Fry the vegetables, add the eggs and cook until set."
                ),

                new Recipe(
                        "Tuna Sandwich",
                        "tuna:1,bread:2,mayonnaise:1,lettuce:1",
                        "Mix tuna with mayonnaise. Add lettuce and place between slices of bread."
                ),

                new Recipe(
                        "Chicken Wrap",
                        "chicken:2,tortilla:1,lettuce:1,tomato:1",
                        "Cook the chicken. Add chicken, lettuce and tomato to a tortilla and wrap."
                ),

                new Recipe(
                        "Beef Pasta",
                        "beef:2,pasta:1,tomato:2,onion:1",
                        "Cook the pasta. Cook the beef with onion and tomato. Combine with pasta."
                ),

                new Recipe(
                        "Tomato Rice",
                        "rice:1,tomato:2,onion:1,garlic:1",
                        "Cook onion and garlic. Add tomato and rice, then cook until the rice is ready."
                ),

                new Recipe(
                        "Chicken Salad",
                        "chicken:2,lettuce:1,tomato:1,cucumber:1",
                        "Cook the chicken. Chop the vegetables and combine everything in a bowl."
                ),

                new Recipe(
                        "Cheese Omelette",
                        "egg:2,cheese:1,milk:1",
                        "Beat the eggs with milk. Cook in a pan and add cheese before folding."
                ),

                new Recipe(
                        "Peanut Butter Sandwich",
                        "bread:2,peanut butter:1,banana:1",
                        "Spread peanut butter on bread and add sliced banana."
                ),

                new Recipe(
                        "Pancakes",
                        "flour:2,egg:2,milk:1,sugar:1",
                        "Mix the ingredients into a batter. Cook small portions in a pan until golden."
                ),

                new Recipe(
                        "French Toast",
                        "bread:2,egg:2,milk:1,sugar:1",
                        "Dip bread in the egg and milk mixture. Fry both sides until golden."
                ),

                new Recipe(
                        "Chicken Soup",
                        "chicken:2,carrot:1,onion:1,potato:2",
                        "Cook chicken with onion. Add carrot and potato with water and simmer until tender."
                ),

                new Recipe(
                        "Beef Stew",
                        "beef:2,potato:2,carrot:1,onion:1",
                        "Brown the beef. Add onion, potato and carrot with water and simmer until tender."
                ),

                new Recipe(
                        "Vegetable Rice",
                        "rice:1,carrot:1,peas:1,corn:1",
                        "Cook the rice. Fry the vegetables and mix them with the cooked rice."
                ),

                new Recipe(
                        "Macaroni Cheese",
                        "macaroni:1,cheese:2,milk:1,butter:1",
                        "Cook the macaroni. Make a cheese sauce using milk and butter, then mix together."
                ),

                new Recipe(
                        "Banana Smoothie",
                        "banana:1,milk:1,yogurt:1,honey:1",
                        "Blend banana, milk, yogurt and honey until smooth."
                ),

                new Recipe(
                        "Chicken and Potato",
                        "chicken:2,potato:2,onion:1,garlic:1",
                        "Cook the chicken with onion and garlic. Add potatoes and cook until tender."
                )
        );

        for (Recipe recipe : recipes) {
            database.recipeDao().insert(recipe);
        }
    }
}