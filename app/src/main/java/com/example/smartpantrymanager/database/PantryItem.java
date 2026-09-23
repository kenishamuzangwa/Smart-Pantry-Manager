package com.example.smartpantrymanager.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantry_items")

public class PantryItem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int quantity;
    private String category;
    private String expiryDate;

    public PantryItem(String name, int quantity, String category, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
