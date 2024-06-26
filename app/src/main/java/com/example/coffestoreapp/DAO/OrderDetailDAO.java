package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.OrderDetailDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

public class OrderDetailDAO {
    SQLiteDatabase database;
    private static OrderDetailDAO instance;

    private OrderDetailDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public static synchronized OrderDetailDAO getInstance(Context context){
        if (instance == null){
            instance = new OrderDetailDAO(context.getApplicationContext());
        }
        return instance;
    }

    public boolean checkDrinkExist(int orderId, int drinkId) {
        String query = "SELECT * FROM " + CreateDatabase.TABLE_ORDER_DETAIL + " WHERE "
                + CreateDatabase.ORDER_DETAIL_DRINK_ID +
                " = " + drinkId + " AND " + CreateDatabase.ORDER_DETAIL_ORDER_ID + " = " + orderId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getNumberDrinkByOrderId(int orderId, int drinkId) {
        int number = 0;
        String query = "SELECT * FROM " + CreateDatabase.TABLE_ORDER_DETAIL + " WHERE "
                + CreateDatabase.ORDER_DETAIL_DRINK_ID +
                " = " + drinkId + " AND " + CreateDatabase.ORDER_DETAIL_ORDER_ID + " = " + orderId;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            number = cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_DETAIL_QUANTITY));
            cursor.moveToNext();
        }
        return number;
    }

    public boolean updateNumber(OrderDetailDTO orderDetailDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.ORDER_DETAIL_QUANTITY, orderDetailDTO.getQuantity());

        long check = database.update(CreateDatabase.TABLE_ORDER_DETAIL, contentValues,
                CreateDatabase.ORDER_DETAIL_ORDER_ID + " = "
                        + orderDetailDTO.getOrderID() + " AND " + CreateDatabase.ORDER_DETAIL_DRINK_ID + " = "
                        + orderDetailDTO.getDrinkID(),
                null);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addOrderDetail(OrderDetailDTO orderDetailDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.ORDER_DETAIL_QUANTITY, orderDetailDTO.getQuantity());
        contentValues.put(CreateDatabase.ORDER_DETAIL_ORDER_ID, orderDetailDTO.getOrderID());
        contentValues.put(CreateDatabase.ORDER_DETAIL_DRINK_ID, orderDetailDTO.getDrinkID());

        long check = database.insert(CreateDatabase.TABLE_ORDER_DETAIL, null, contentValues);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }
}
