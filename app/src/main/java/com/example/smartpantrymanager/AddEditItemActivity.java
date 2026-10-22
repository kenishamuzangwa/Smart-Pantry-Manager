package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.app.DatePickerDialog;
import android.widget.TextView;

import com.example.smartpantrymanager.database.PantryDatabase;
import com.example.smartpantrymanager.database.PantryItem;

import java.util.Calendar;

public class AddEditItemActivity extends AppCompatActivity {

    private EditText editItemName;
    private EditText editQuantity;
    private EditText editExpiryDate;
    private Spinner editCategory;
    private Button btnSaveItem;
    private Button btnCancelItem;
    private PantryDatabase database;
    private boolean editMode = false;
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        database = PantryDatabase.getInstance(this);
        Intent intent = getIntent();

        if (intent.hasExtra("item_id")) {
            editMode = true;
            itemId = intent.getIntExtra("item_id", -1);
        }



        editItemName = findViewById(R.id.editItemName);
        editQuantity = findViewById(R.id.editQuantity);
        editExpiryDate = findViewById(R.id.editExpiryDate);
        editCategory = findViewById(R.id.editCategory);
        btnSaveItem = findViewById(R.id.btnSaveItem);
        btnCancelItem = findViewById(R.id.btnCancelItem);

        TextView title = findViewById(R.id.textAddEditTitle);

        if (editMode) {
            title.setText("Edit Pantry Item");
            btnSaveItem.setText("UPDATE ITEM");
        }

        String[] categories = {
                "Food",
                "Drinks",
                "Snacks",
                "Canned Goods",
                "Grains",
                "Other"
        };

        //Category Adapter
        android.widget.ArrayAdapter<String> categoryAdapter =
                new android.widget.ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        editCategory.setAdapter(categoryAdapter);

        if (editMode) {
            new Thread(() -> {
                PantryItem item = database.pantryItemDao().getItemById(itemId);

                runOnUiThread(() -> {
                    if (item != null) {
                        editItemName.setText(item.getName());
                        editQuantity.setText(String.valueOf(item.getQuantity()));
                        editExpiryDate.setText(item.getExpiryDate());

                        int categoryPosition = ((ArrayAdapter<String>)
                                editCategory.getAdapter()).getPosition(item.getCategory());


                        if (categoryPosition >= 0) {
                            editCategory.setSelection(categoryPosition);
                        }


                    }
                });
            }).start();


        }

        editExpiryDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            this,
                            (view, selectedYear, selectedMonth, selectedDay) -> {

                                String date = selectedDay + "/"
                                        + (selectedMonth + 1) + "/"
                                        + selectedYear;

                                editExpiryDate.setText(date);
                            },
                            year,
                            month,
                            day
                    );

            datePickerDialog.show();
        });


        btnCancelItem.setOnClickListener(v -> {
            finish();
        });

        btnSaveItem.setOnClickListener(v -> {

            String name = editItemName.getText().toString().trim();
            String quantityText = editQuantity.getText().toString().trim();
            String expiryDate = editExpiryDate.getText().toString().trim();
            String category = editCategory.getSelectedItem().toString();

            if (name.isEmpty()) {
                editItemName.setError("Please enter an item name");
                return;
            }

            if (quantityText.isEmpty()) {
                editQuantity.setError("Please enter a quantity");
                return;
            }

            if (expiryDate.isEmpty()) {
                editExpiryDate.setError("Please select an expiry date");
                return;
            }

            int quantity;

            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException e) {
                editQuantity.setError("Please enter a valid number");
                return;
            }

            if (quantity <= 0) {
                editQuantity.setError("Quantity must be greater than 0");
                return;
            }

            PantryItem pantryItem = new PantryItem(
                    name,
                    quantity,
                    category,
                    expiryDate
            );

            new Thread(() -> {

                if (editMode) {
                    pantryItem.setId(itemId);
                    database.pantryItemDao().update(pantryItem);
                } else {
                    database.pantryItemDao().insert(pantryItem);
                }

                runOnUiThread(() -> {
                    finish();
                });

            }).start();

        });
    }
}