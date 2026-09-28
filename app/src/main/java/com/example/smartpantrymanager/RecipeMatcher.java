package com.example.smartpantrymanager;

public class RecipeMatcher {

    public static boolean matches(String recipeIngredients, java.util.Map<String, Integer> pantry) {

        String[] requiredIngredients = recipeIngredients.split(",");

        for (String ingredient : requiredIngredients) {

            String[] parts = ingredient.split(":");

            String ingredientName = normalizeIngredientName(parts[0]);
            int requiredQuantity = Integer.parseInt(parts[1].trim());

            int pantryQuantity = pantry.getOrDefault(ingredientName, 0);

            if (pantryQuantity < requiredQuantity) {
                return false;
            }
        }

        return true;
    }

    private static String normalizeIngredientName(String name) {

        name = name.trim().toLowerCase();

        if (name.endsWith("ies")) {
            return name.substring(0, name.length() - 3) + "y";
        }

        if (name.endsWith("s") && !name.endsWith("ss")) {
            return name.substring(0, name.length() - 1);
        }

        return name;
    }
}
