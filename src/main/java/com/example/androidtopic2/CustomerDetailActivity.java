package com.example.androidtopic2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topic2b.R;

public class CustomerDetailActivity extends AppCompatActivity {

    private TextView textViewCustomerName, textViewYYYYMM, textViewAddress, textViewUsedNumElectric, textViewElecUserType;
    private Button buttonFirst, buttonPrevious, buttonNext, buttonLast;
    private DatabaseHelper databaseHelper;

    private int currentCustomerId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        textViewCustomerName = findViewById(R.id.textViewCustomerName);
        textViewYYYYMM = findViewById(R.id.textViewYYYYMM);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewUsedNumElectric = findViewById(R.id.textViewUsedNumElectric);
        textViewElecUserType = findViewById(R.id.textViewElecUserType);

        buttonFirst = findViewById(R.id.buttonFirst);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        buttonLast = findViewById(R.id.buttonLast);

        databaseHelper = new DatabaseHelper(this);


        loadCustomerData(currentCustomerId);


        buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCustomerId = 1;
                loadCustomerData(currentCustomerId);
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCustomerId > 1) {
                    currentCustomerId--;
                    loadCustomerData(currentCustomerId);
                } else {
                    Toast.makeText(CustomerDetailActivity.this, "Đây là khách hàng đầu tiên.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCustomerId++;
                loadCustomerData(currentCustomerId);
            }
        });

        buttonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.getAllCustomers();
                if (cursor != null && cursor.moveToLast()) {
                    currentCustomerId = cursor.getInt(cursor.getColumnIndex("ID"));
                    loadCustomerData(currentCustomerId);
                }
            }
        });
    }

    private void loadCustomerData(int customerId) {
        Cursor cursor = databaseHelper.getCustomerById(customerId);
        if (cursor != null && cursor.moveToFirst()) {

            int nameIndex = cursor.getColumnIndex("NAME");
            int yyyyMMIndex = cursor.getColumnIndex("YYYYMM");
            int addressIndex = cursor.getColumnIndex("ADDRESS");
            int usedElectricityIndex = cursor.getColumnIndex("USED_NUM_ELECTRIC");
            int elecUserTypeIdIndex = cursor.getColumnIndex("ELEC_USER_TYPE_ID");


            if (nameIndex != -1 && yyyyMMIndex != -1 && addressIndex != -1 && usedElectricityIndex != -1 && elecUserTypeIdIndex != -1) {
                String name = cursor.getString(nameIndex);
                String yyyyMM = cursor.getString(yyyyMMIndex);
                String address = cursor.getString(addressIndex);
                double usedElectricity = cursor.getDouble(usedElectricityIndex);
                int elecUserTypeId = cursor.getInt(elecUserTypeIdIndex);


                textViewCustomerName.setText("Name: " + name);
                textViewYYYYMM.setText("YYYYMM: " + yyyyMM);
                textViewAddress.setText("Address: " + address);
                textViewUsedNumElectric.setText("Used Electricity: " + usedElectricity + " kWh");


                Cursor userTypeCursor = databaseHelper.getAllUserTypes();
                if (userTypeCursor != null) {
                    boolean userTypeFound = false;
                    while (userTypeCursor.moveToNext()) {

                        int userTypeIdIndex = userTypeCursor.getColumnIndex("ID");
                        int userTypeNameIndex = userTypeCursor.getColumnIndex("ELEC_USER_TYPE_NAME");


                        if (userTypeIdIndex != -1 && userTypeNameIndex != -1) {
                            if (userTypeCursor.getInt(userTypeIdIndex) == elecUserTypeId) {
                                String userTypeName = userTypeCursor.getString(userTypeNameIndex);
                                textViewElecUserType.setText("Electric User Type: " + userTypeName);
                                userTypeFound = true;
                                break;
                            }
                        }
                    }


                    if (!userTypeFound) {
                        textViewElecUserType.setText("Electric User Type: Không xác định");
                    }
                    userTypeCursor.close();
                }
            } else {
                Toast.makeText(this, "Dữ liệu không đầy đủ", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Không tìm thấy khách hàng", Toast.LENGTH_SHORT).show();
        }
    }

}
