package com.example.ongminipeta1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        findViewById(R.id.personalInfo).setOnClickListener(v ->
                showMessage(R.string.msg_personal_info));

        findViewById(R.id.securityOption).setOnClickListener(v ->
                showMessage(R.string.msg_security));

        findViewById(R.id.notificationsOption).setOnClickListener(v ->
                showMessage(R.string.msg_notifications));

        findViewById(R.id.languageOption).setOnClickListener(v ->
                showMessage(R.string.msg_language));

        findViewById(R.id.helpOption).setOnClickListener(v ->
                showMessage(R.string.msg_help));

        findViewById(R.id.aboutOption).setOnClickListener(v ->
                showMessage(R.string.msg_about));

        findViewById(R.id.logoutOption).setOnClickListener(v -> {

            Intent intent = new Intent(
                    SettingsActivity.this, MainActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();
        });
    }

    private void showMessage(@StringRes int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}