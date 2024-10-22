package com.example.androidtopic2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topic2b.R;

public class UpdateCustomerActivity extends AppCompatActivity {

    private EditText editTextCustomerName, editTextYYYYMM, editTextAddress, editTextUsedNumElectric;
    private Spinner spinnerUserType;
    private Button buttonSaveChanges;
    private int customerId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);


        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextYYYYMM = findViewById(R.id.editTextYYYYMM);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextUsedNumElectric = findViewById(R.id.editTextUsedNumElectric);
        spinnerUserType = findViewById(R.id.spinnerUserType);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);

        databaseHelper = new DatabaseHelper(this);


        customerId = getIntent().getIntExtra("CUSTOMER_ID", -1);

        if (customerId != -1) {

            loadCustomerData(customerId);
        } else {
            Toast.makeText(this, "Invalid customer ID", Toast.LENGTH_SHORT).show();
        }


        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCustomerData();
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


            if (nameIndex != -1 && yyyyMMIndex != -1 && addressIndex != -1 &&
                    usedElectricityIndex != -1 && elecUserTypeIdIndex != -1) {
                String name = cursor.getString(nameIndex);
                String yyyyMM = cursor.getString(yyyyMMIndex);
                String address = cursor.getString(addressIndex);
                double usedElectricity = cursor.getDouble(usedElectricityIndex);
                int elecUserTypeId = cursor.getInt(elecUserTypeIdIndex);


                editTextCustomerName.setText(name);
                editTextYYYYMM.setText(yyyyMM);
                editTextAddress.setText(address);
                editTextUsedNumElectric.setText(String.valueOf(usedElectricity));


                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.elec_user_types, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUserType.setAdapter(adapter);

                if (elecUserTypeId == 1) {
                    spinnerUserType.setSelection(0); // Private
                } else if (elecUserTypeId == 2) {
                    spinnerUserType.setSelection(1); // Business
                }
            } else {
                Toast.makeText(this, "Error: Column not found in the database", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No data found for customer ID: " + customerId, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCustomerData() {
        String name = editTextCustomerName.getText().toString();
        String yyyyMM = editTextYYYYMM.getText().toString();
        String address = editTextAddress.getText().toString();
        double usedElectricity = Double.parseDouble(editTextUsedNumElectric.getText().toString());
        int userTypeId = spinnerUserType.getSelectedItemPosition() + 1; // Position 0 is Private, 1 is Business

        boolean isUpdated = databaseHelper.updateCustomer(customerId, name, yyyyMM, address, usedElectricity, userTypeId);

        if (isUpdated) {
            Toast.makeText(this, "Customer updated successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity và trở lại
        } else {
            Toast.makeText(this, "Failed to update customer", Toast.LENGTH_SHORT).show();
        }
    }
}
