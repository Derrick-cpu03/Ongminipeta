package com.example.ongminipeta1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String username = getIntent().getStringExtra("username");

        if (username != null) {
            TextView greeting = findViewById(R.id.userGreeting);
            if (greeting != null) {
                greeting.setText(getString(R.string.hello_user_param, username.toUpperCase(Locale.ROOT)));
            }
        }

        View settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });
    }
}