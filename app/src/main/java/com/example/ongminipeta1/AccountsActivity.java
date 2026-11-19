package com.example.ongminipeta1.;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AccountsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView accountsList;
    private TextView emptyMessage;
    private ArrayList<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        databaseHelper = new DatabaseHelper(this);

        accountsList = findViewById(R.id.accountsList);
        emptyMessage = findViewById(R.id.emptyMessage);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        accountsList.setOnItemClickListener((parent, view, position, id) -> {
            if (usernames != null && position < usernames.size()) {
                String selectedUsername = usernames.get(position);

                Toast.makeText(this,
                        getString(R.string.selected_account, selectedUsername),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(
                        AccountsActivity.this,
                        DashboardActivity.class
                );

                intent.putExtra("username", selectedUsername);
                startActivity(intent);
            }
        });

        loadAccounts();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null && accountsList != null) {
            loadAccounts();
        }
    }

    private void loadAccounts() {
        usernames = databaseHelper.getAllUsernames();
        ArrayList<String> displayList = new ArrayList<>();

        for (String username : usernames) {
            displayList.add((displayList.size() + 1) + ".  " + username);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );

        accountsList.setAdapter(adapter);

        if (usernames.isEmpty()) {
            emptyMessage.setVisibility(TextView.VISIBLE);
            accountsList.setVisibility(ListView.GONE);
        } else {
            emptyMessage.setVisibility(TextView.GONE);
            accountsList.setVisibility(ListView.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}{
}
