package com.example.ongminipeta1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        databaseHelper = new DatabaseHelper(this);

        EditText signupUsername = findViewById(R.id.signupUsername);
        EditText signupPassword = findViewById(R.id.signupPassword);
        EditText confirmPassword = findViewById(R.id.confirmPassword);

        Button registerButton = findViewById(R.id.registerButton);
        Button backToLoginButton = findViewById(R.id.backToLoginButton);

        registerButton.setOnClickListener(v -> {

            String username = signupUsername.getText().toString().trim();
            String password = signupPassword.getText().toString();
            String confirm = confirmPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {

                Toast.makeText(this, R.string.please_fill_all_fields,
                        Toast.LENGTH_SHORT).show();

            } else if (username.length() < 3) {

                Toast.makeText(this, R.string.username_min_length,
                        Toast.LENGTH_SHORT).show();

            } else if (password.length() < 6) {

                Toast.makeText(this, R.string.password_min_length,
                        Toast.LENGTH_SHORT).show();

            } else if (!Objects.equals(password, confirm)) {

                Toast.makeText(this, R.string.passwords_do_not_match,
                        Toast.LENGTH_SHORT).show();

            } else {

                boolean registered =
                        databaseHelper.registerUser(username, password);

                if (registered) {

                    Toast.makeText(this, R.string.account_created,
                            Toast.LENGTH_SHORT).show();

                    finish();

                } else {

                    Toast.makeText(this, R.string.username_exists,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        backToLoginButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
