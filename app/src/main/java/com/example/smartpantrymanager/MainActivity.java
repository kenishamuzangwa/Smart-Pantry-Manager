package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.DatePickerDialog;
import java.util.Calendar;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.smartpantrymanager.database.PantryDatabase;
import com.example.smartpantrymanager.database.PantryItem;

public class MainActivity extends AppCompatActivity {


    private Button btnAddItem;
    private ListView listViewPantry;
    private PantryDatabase database;

    private TextView txtTotalItems;
    private TextView txtTotalCategories;
    private TextView txtExpiringSoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        database = PantryDatabase.getInstance(this);
        Toast.makeText(this, "Pantry database connected", Toast.LENGTH_LONG).show();




        btnAddItem = findViewById(R.id.btnAddItem);
        listViewPantry = findViewById(R.id.listViewPantry);

        txtTotalItems = findViewById(R.id.txtTotalItems);
        txtTotalCategories = findViewById(R.id.txtTotalCategories);
        txtExpiringSoon = findViewById(R.id.txtExpiringSoon);

        loadPantryItems();

        listViewPantry.setOnItemClickListener((parent, view, position, id) -> {

            new Thread(() -> {

                PantryItem selectedItem =
                        database.pantryItemDao().getAllItems().get(position);

                runOnUiThread(() -> {

                    new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Manage Item")
                            .setMessage("What would you like to do with "
                                    + selectedItem.getName() + "?")

                            .setPositiveButton("Edit", (dialog, which) -> {

                                Intent intent = new Intent(
                                        MainActivity.this,
                                        AddEditItemActivity.class
                                );

                                intent.putExtra("item_id", selectedItem.getId());

                                startActivity(intent);



                            })

                            .setNeutralButton("Delete", (dialog, which) -> {

                                new Thread(() -> {

                                    database.pantryItemDao().delete(selectedItem);

                                    runOnUiThread(this::loadPantryItems);

                                }).start();

                            })

                            .setNegativeButton("Cancel", null)

                            .show();

                });

            }).start();

        });

        String[] categories = {
                "Food",
                "Drinks",
                "Snacks",
                "Canned Goods",
                "Grains",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        btnAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditItemActivity.class
            );

            startActivity(intent);
        });





        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_pantry) {
                return true;

            } else if (item.getItemId() == R.id.nav_recipes) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SuggestedRecipesActivity.class
                );

                startActivity(intent);
                return true;

            } else if (item.getItemId() == R.id.nav_settings) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                );

                startActivity(intent);
                return true;
            }

            return false;
        });


    }

    private void loadPantryItems() {
        new Thread(() -> {
            java.util.List<PantryItem> items =
                    database.pantryItemDao().getAllItems();

            // Count the different categories.
            java.util.Set<String> categories = new java.util.HashSet<>();

            // Count items expiring within the next 7 days.
            int expiringCount = 0;

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            Calendar sevenDaysLater = (Calendar) today.clone();
            sevenDaysLater.add(Calendar.DAY_OF_YEAR, 7);

            for (PantryItem item : items) {
                if (item.getCategory() != null
                        && !item.getCategory().trim().isEmpty()) {
                    categories.add(item.getCategory().trim());
                }

                if (item.getExpiryDate() != null
                        && !item.getExpiryDate().trim().isEmpty()) {
                    try {
                        String[] parts = item.getExpiryDate().split("/");

                        if (parts.length == 3) {
                            Calendar expiry = Calendar.getInstance();
                            expiry.set(
                                    Integer.parseInt(parts[2]),
                                    Integer.parseInt(parts[1]) - 1,
                                    Integer.parseInt(parts[0]),
                                    0, 0, 0
                            );
                            expiry.set(Calendar.MILLISECOND, 0);

                            if (!expiry.before(today)
                                    && !expiry.after(sevenDaysLater)) {
                                expiringCount++;
                            }
                        }
                    } catch (NumberFormatException e) {
                        Log.e("PantrySummary",
                                "Could not read an item's expiry date", e);
                    }
                }
            }

            final int totalItems = items.size();
            final int totalCategories = categories.size();
            final int totalExpiring = expiringCount;

            runOnUiThread(() -> {
                PantryAdapter pantryAdapter =
                        new PantryAdapter(MainActivity.this, items);

                listViewPantry.setAdapter(pantryAdapter);

                txtTotalItems.setText(String.valueOf(totalItems));
                txtTotalCategories.setText(String.valueOf(totalCategories));
                txtExpiringSoon.setText(String.valueOf(totalExpiring));
            });
        }).start();


}
    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }
}
