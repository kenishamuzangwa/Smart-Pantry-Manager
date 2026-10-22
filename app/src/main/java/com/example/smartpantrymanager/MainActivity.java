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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.smartpantrymanager.database.PantryDatabase;
import com.example.smartpantrymanager.database.PantryItem;

public class MainActivity extends AppCompatActivity {

    private EditText editTextItemName;
    private EditText editTextQuantity;
    private EditText editTextExpiryDate;
    private Spinner spinnerCategory;
    private Button btnAddItem;
    private ListView listViewPantry;
    private PantryDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        database = PantryDatabase.getInstance(this);
        Toast.makeText(this, "Pantry database connected", Toast.LENGTH_LONG).show();



        editTextItemName = findViewById(R.id.editTextItemName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextExpiryDate = findViewById(R.id.editTextExpiryDate);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAddItem = findViewById(R.id.btnAddItem);
        listViewPantry = findViewById(R.id.listViewPantry);

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

        spinnerCategory.setAdapter(adapter);

        editTextExpiryDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        String date = selectedDay + "/" +
                                (selectedMonth + 1) + "/" +
                                selectedYear;

                        editTextExpiryDate.setText(date);
                    },
                    year,
                    month,
                    day
            );

            datePickerDialog.show();
        });

        btnAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditItemActivity.class
            );

            startActivity(intent);
        });

        Button btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);

        btnSuggestedRecipes.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);



        });

        // Settings button
        Button btnSettings = findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
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

            runOnUiThread(() -> {

                PantryAdapter pantryAdapter =
                        new PantryAdapter(MainActivity.this, items);

                listViewPantry.setAdapter(pantryAdapter);
            });

        }).start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }
}
