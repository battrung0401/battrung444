package com.example.androidtopic2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topic2b.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IncreasePriceActivity extends AppCompatActivity {

    private EditText editTextIncreaseAmount;
    private Button buttonIncreasePrice;
    private TextView textViewNotification;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_price);

        editTextIncreaseAmount = findViewById(R.id.editTextIncreaseAmount);
        buttonIncreasePrice = findViewById(R.id.buttonIncreasePrice);
        textViewNotification = findViewById(R.id.textViewNotification);

        databaseHelper = new DatabaseHelper(this);

        buttonIncreasePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePriceIncrease();
            }
        });
    }

    private void handlePriceIncrease() {

        String increaseAmountStr = editTextIncreaseAmount.getText().toString();
        if (increaseAmountStr.isEmpty()) {
            Toast.makeText(IncreasePriceActivity.this, "Please enter the amount to increase", Toast.LENGTH_SHORT).show();
            return;
        }

        double increaseAmount = Double.parseDouble(increaseAmountStr);


        int userTypeId = 1;


        boolean isUpdated = databaseHelper.increaseElectricPrice(userTypeId, increaseAmount);
        if (isUpdated) {

            String userTypeName = (userTypeId == 1) ? "Private" : "Business";
            String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
            String notificationMessage = String.format("Already increase electric unit price for user type %s with amount %.2f at %s", userTypeName, increaseAmount, time);


            textViewNotification.setText(notificationMessage);
            textViewNotification.setVisibility(View.VISIBLE);


            Toast.makeText(IncreasePriceActivity.this, "Electric unit price increased successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(IncreasePriceActivity.this, "Failed to increase electric unit price", Toast.LENGTH_SHORT).show();
        }
    }
}
