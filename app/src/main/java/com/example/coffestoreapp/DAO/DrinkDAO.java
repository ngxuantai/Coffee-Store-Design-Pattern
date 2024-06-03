package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.DrinkDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DrinkDAO {
    SQLiteDatabase database;

    public DrinkDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addDrink(DrinkDTO drinkDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.DRINK_CATEGORY_ID, drinkDTO.getCategoryID());
        contentValues.put(CreateDatabase.DRINK_NAME, drinkDTO.getDrinkName());
        contentValues.put(CreateDatabase.DRINK_PRICE, drinkDTO.getPrice());
        contentValues.put(CreateDatabase.DRINK_IMAGE, drinkDTO.getImage());
        contentValues.put(CreateDatabase.DRINK_STATUS, "true");

        long check = database.insert(CreateDatabase.TABLE_DRINK, null, contentValues);

        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteDrink(int drinkId) {
        long check = database.delete(CreateDatabase.TABLE_DRINK, CreateDatabase.DRINK_ID + " = " + drinkId, null);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editDrink(DrinkDTO drinkDTO, int drinkId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.DRINK_CATEGORY_ID, drinkDTO.getCategoryID());
        contentValues.put(CreateDatabase.DRINK_NAME, drinkDTO.getDrinkName());
        contentValues.put(CreateDatabase.DRINK_PRICE, drinkDTO.getPrice());
        contentValues.put(CreateDatabase.DRINK_IMAGE, drinkDTO.getImage());
        contentValues.put(CreateDatabase.DRINK_STATUS, drinkDTO.getStatus());

        long check = database.update(CreateDatabase.TABLE_DRINK, contentValues,
                CreateDatabase.DRINK_ID + " = " + drinkId, null);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<DrinkDTO> getListDrinkByCategoryId(int categoryId) {
        List<DrinkDTO> drinkDTOList = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TABLE_DRINK + " WHERE " + CreateDatabase.DRINK_CATEGORY_ID
                + " = " + categoryId;
        Cursor cursor = database.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DrinkDTO drinkDTO = new DrinkDTO.DrinkBuilder()
                            .setDrinkID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_ID)))
                            .setCategoryID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_CATEGORY_ID)))
                            .setDrinkName(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_NAME)))
                            .setPrice(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_PRICE)))
                            .setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_STATUS)))
                            .setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.DRINK_IMAGE)))
                            .build();
                    drinkDTOList.add(drinkDTO);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return drinkDTOList;
    }

    public DrinkDTO getDrinkById(int drinkId) {
        String query = "SELECT * FROM " + CreateDatabase.TABLE_DRINK + " WHERE " + CreateDatabase.DRINK_ID + " = "
                + drinkId;
        Cursor cursor = database.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                return new DrinkDTO.DrinkBuilder()
                        .setDrinkID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_ID)))
                        .setCategoryID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_CATEGORY_ID)))
                        .setDrinkName(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_NAME)))
                        .setPrice(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_PRICE)))
                        .setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_STATUS)))
                        .setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.DRINK_IMAGE)))
                        .build();
            }
        } finally {
            cursor.close();
        }

        return null;
    }
}
