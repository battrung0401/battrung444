package com.example.androidtopic2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topic2b.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchCustomerActivity extends AppCompatActivity {

    private EditText editTextSearchCustomer;
    private Button buttonSearchCustomer;
    private ListView listViewSearchResults;
    private DatabaseHelper databaseHelper;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer);

        editTextSearchCustomer = findViewById(R.id.editTextSearchCustomer);
        buttonSearchCustomer = findViewById(R.id.buttonSearchCustomer);
        listViewSearchResults = findViewById(R.id.listViewSearchResults);

        databaseHelper = new DatabaseHelper(this);

        buttonSearchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = editTextSearchCustomer.getText().toString();
                if (searchQuery.isEmpty()) {
                    Toast.makeText(SearchCustomerActivity.this, "Please enter a name or address to search.", Toast.LENGTH_SHORT).show();
                    return;
                }


                List<HashMap<String, String>> results = databaseHelper.searchCustomers(searchQuery);


                if (results.isEmpty()) {
                    Toast.makeText(SearchCustomerActivity.this, "No customers found.", Toast.LENGTH_SHORT).show();
                } else {
                    simpleAdapter = new SimpleAdapter(
                            SearchCustomerActivity.this,
                            results,
                            android.R.layout.simple_list_item_2,
                            new String[]{"NAME", "ADDRESS"},
                            new int[]{android.R.id.text1, android.R.id.text2}
                    );
                    listViewSearchResults.setAdapter(simpleAdapter);
                }
            }
        });
    }
}
