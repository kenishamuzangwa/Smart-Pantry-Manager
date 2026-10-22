package com.example.smartpantrymanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartpantrymanager.database.Recipe;

import java.util.List;

public class RecipeAdapter extends BaseAdapter {

    private final Context context;
    private final List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recipes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.WHITE);
        background.setCornerRadius(dp(16));
        background.setStroke(dp(1), Color.rgb(220, 229, 216));

        card.setBackground(background);
        card.setElevation(dp(2));

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(dp(4), dp(6), dp(4), dp(6));
        card.setLayoutParams(cardParams);

        TextView recipeName = new TextView(context);
        recipeName.setText("🍲  " + recipes.get(position).getName());
        recipeName.setTextSize(18);
        recipeName.setTypeface(null, Typeface.BOLD);
        recipeName.setTextColor(Color.rgb(36, 90, 64));

        card.addView(recipeName);

        TextView subtitle = new TextView(context);
        subtitle.setText("Tap to view ingredients and instructions");
        subtitle.setTextSize(13);
        subtitle.setTextColor(Color.rgb(101, 117, 101));
        subtitle.setPadding(0, dp(6), 0, 0);

        card.addView(subtitle);

        return card;
    }

    private int dp(int value) {
        return (int) (value * context.getResources()
                .getDisplayMetrics().density + 0.5f);
    }
}
