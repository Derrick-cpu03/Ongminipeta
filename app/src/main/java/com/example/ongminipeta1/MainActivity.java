package com.example.ongminipeta1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);

        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);
        Button accountsButton = findViewById(R.id.accountsButton);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.please_enter_credentials,
                        Toast.LENGTH_SHORT).show();
            } else if (databaseHelper.checkUser(username, password)) {
                Toast.makeText(this, R.string.login_successful,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(
                        MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.incorrect_credentials,
                        Toast.LENGTH_SHORT).show();
            }
        });

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        accountsButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this, AccountsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}