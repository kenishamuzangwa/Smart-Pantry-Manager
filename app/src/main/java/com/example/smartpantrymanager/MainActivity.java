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

                                android.view.View editView =
                                        getLayoutInflater().inflate(
                                                R.layout.dialog_edit_item,
                                                null
                                        );

                                android.widget.EditText editItemName =
                                        editView.findViewById(R.id.editItemName);

                                android.widget.EditText editQuantity =
                                        editView.findViewById(R.id.editQuantity);

                                android.widget.EditText editExpiryDate =
                                        editView.findViewById(R.id.editExpiryDate);

                                android.widget.Spinner editCategory =
                                        editView.findViewById(R.id.editCategory);

                                editItemName.setText(selectedItem.getName());
                                editQuantity.setText(String.valueOf(selectedItem.getQuantity()));
                                editExpiryDate.setText(selectedItem.getExpiryDate());

                                String[] categories = {
                                        "Food",
                                        "Drinks",
                                        "Snacks",
                                        "Canned Goods",
                                        "Grains",
                                        "Other"
                                };

                                android.widget.ArrayAdapter<String> categoryAdapter =
                                        new android.widget.ArrayAdapter<>(
                                                MainActivity.this,
                                                android.R.layout.simple_spinner_item,
                                                categories
                                        );

                                categoryAdapter.setDropDownViewResource(
                                        android.R.layout.simple_spinner_dropdown_item
                                );

                                editCategory.setAdapter(categoryAdapter);

                                new android.app.AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Edit Item")
                                        .setView(editView)
                                        .setPositiveButton("Save Changes", (editDialog, editWhich) -> {

                                            String newName = editItemName.getText().toString().trim();
                                            int newQuantity = Integer.parseInt(
                                                    editQuantity.getText().toString().trim()
                                            );
                                            String newExpiryDate = editExpiryDate.getText().toString().trim();
                                            String newCategory = editCategory.getSelectedItem().toString();

                                            selectedItem.setName(newName);
                                            selectedItem.setQuantity(newQuantity);
                                            selectedItem.setExpiryDate(newExpiryDate);
                                            selectedItem.setCategory(newCategory);

                                            new Thread(() -> {

                                                database.pantryItemDao().update(selectedItem);

                                                runOnUiThread(this::loadPantryItems);

                                            }).start();
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .show();

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

            String name = editTextItemName.getText().toString().trim();
            String quantityText = editTextQuantity.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String expiryDate = editTextExpiryDate.getText().toString().trim();

            if (name.isEmpty() || quantityText.isEmpty() || expiryDate.isEmpty()) {
                return;
            }

            int quantity = Integer.parseInt(quantityText);

            PantryItem pantryItem = new PantryItem(
                    name,
                    quantity,
                    category,
                    expiryDate
            );

            new Thread(() -> {
                database.pantryItemDao().insert(pantryItem);
                runOnUiThread(this::loadPantryItems);
            }).start();

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


    }
    private void loadPantryItems() {

        new Thread(() -> {

            java.util.List<PantryItem> items =
                    database.pantryItemDao().getAllItems();

            runOnUiThread(() -> {

                String[] itemNames = new String[items.size()];

                for (int i = 0; i < items.size(); i++) {
                    PantryItem item = items.get(i);

                    itemNames[i] = item.getName()
                            + " - Qty: " + item.getQuantity()
                            + " - " + item.getCategory()
                            + " - Exp: " + item.getExpiryDate();
                }

                ArrayAdapter<String> listAdapter =
                        new ArrayAdapter<>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                itemNames
                        );

                listViewPantry.setAdapter(listAdapter);
            });

        }).start();
    }
}