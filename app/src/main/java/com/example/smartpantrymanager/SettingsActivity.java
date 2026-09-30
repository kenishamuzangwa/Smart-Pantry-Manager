package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Button;
import android.widget.Switch;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Connect the profile fields
        EditText editTextProfileName =
                findViewById(R.id.editTextProfileName);

        EditText editTextProfileEmail =
                findViewById(R.id.editTextProfileEmail);

        // Connect the Save Profile button
        Button btnSaveProfile =
                findViewById(R.id.btnSaveProfile);

        // Connect the notifications switch
        Switch switchNotifications =
                findViewById(R.id.switchNotifications);

        // Access the app's local settings storage
        SharedPreferences preferences =
                getSharedPreferences("PantrySettings", MODE_PRIVATE);

// Load previously saved profile details
        editTextProfileName.setText(
                preferences.getString("profile_name", ""));

        editTextProfileEmail.setText(
                preferences.getString("profile_email", ""));

        // Load the saved notification setting
        boolean notificationsEnabled =
                preferences.getBoolean("notifications_enabled", false);

        switchNotifications.setChecked(notificationsEnabled);

// Save the profile when the button is clicked
        btnSaveProfile.setOnClickListener(v -> {

            String name = editTextProfileName.getText().toString().trim();
            String email = editTextProfileEmail.getText().toString().trim();

            // Save the notification setting whenever the switch changes

            // Check that both fields are filled in
            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this,
                        "Please enter your name and email",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Store the profile details
            preferences.edit()
                    .putString("profile_name", name)
                    .putString("profile_email", email)
                    .apply();

            Toast.makeText(this,
                    "Profile saved successfully!",
                    Toast.LENGTH_SHORT).show();
        });

        // Save the notification setting whenever the switch changes
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {

            preferences.edit()
                    .putBoolean("notifications_enabled", isChecked)
                    .apply();
        });

    }


}