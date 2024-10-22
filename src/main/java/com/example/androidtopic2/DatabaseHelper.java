package com.example.androidtopic2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ElectricityDB";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_CUSTOMERS = "customers";
    private static final String TABLE_ELEC_USER_TYPE = "electric_user_type";

    // Customers table columns
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_YYYYMM = "YYYYMM";
    private static final String COLUMN_ADDRESS = "ADDRESS";
    private static final String COLUMN_USED_NUM_ELECTRIC = "USED_NUM_ELECTRIC";
    private static final String COLUMN_ELEC_USER_TYPE_ID = "ELEC_USER_TYPE_ID";
    private SQLiteDatabase database;


    private static final String COLUMN_ELEC_USER_TYPE_NAME = "ELEC_USER_TYPE_NAME";
    private static final String COLUMN_UNIT_PRICE = "UNIT_PRICE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_ELEC_USER_TYPE = "CREATE TABLE " + TABLE_ELEC_USER_TYPE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_ELEC_USER_TYPE_NAME + " TEXT,"
                + COLUMN_UNIT_PRICE + " REAL" + ")";
        db.execSQL(CREATE_TABLE_ELEC_USER_TYPE);


        String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_CUSTOMERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                + COLUMN_YYYYMM + " TEXT," + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_USED_NUM_ELECTRIC + " REAL," + COLUMN_ELEC_USER_TYPE_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ELEC_USER_TYPE_ID + ") REFERENCES " + TABLE_ELEC_USER_TYPE + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_TABLE_CUSTOMERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELEC_USER_TYPE);
        onCreate(db);
    }


    public long addCustomer(String name, String yyyyMM, String address, double usedNumElectric, int elecUserTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YYYYMM, yyyyMM);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_USED_NUM_ELECTRIC, usedNumElectric);
        values.put(COLUMN_ELEC_USER_TYPE_ID, elecUserTypeId);

        long id = db.insert(TABLE_CUSTOMERS, null, values);
        db.close();
        return id;
    }


    public Cursor getAllUserTypes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ELEC_USER_TYPE, null, null, null, null, null, null);
    }


    public List<HashMap<String, String>> searchCustomers(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<HashMap<String, String>> customerList = new ArrayList<>();


        String sql = "SELECT * FROM " + TABLE_CUSTOMERS + " WHERE NAME LIKE ? OR ADDRESS LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%", "%" + query + "%"};
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> customer = new HashMap<>();


                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS);

                if (nameIndex != -1) {
                    customer.put("NAME", cursor.getString(nameIndex));
                }

                if (addressIndex != -1) {
                    customer.put("ADDRESS", cursor.getString(addressIndex));
                }

                customerList.add(customer);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return customerList;
    }


    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CUSTOMERS, null, null, null, null, null, null);
    }


    public Cursor getCustomerById(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CUSTOMERS, null, COLUMN_ID + "=?", new String[]{String.valueOf(customerId)}, null, null, null);
    }


    public boolean updateCustomer(int id, String name, String yyyyMM, String address, double usedElectricity, int userTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YYYYMM, yyyyMM);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_USED_NUM_ELECTRIC, usedElectricity);
        values.put(COLUMN_ELEC_USER_TYPE_ID, userTypeId);

        int rowsUpdated = db.update(TABLE_CUSTOMERS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return rowsUpdated > 0;
    }


    public boolean increaseElectricPrice(int userTypeId, double increaseAmount) {

        ContentValues values = new ContentValues();
        values.put("ELECTRIC_PRICE", increaseAmount);


        int rowsAffected = database.update("electric_price", values, "user_type_id = ?", new String[]{String.valueOf(userTypeId)});


        return rowsAffected > 0;
    }
}
