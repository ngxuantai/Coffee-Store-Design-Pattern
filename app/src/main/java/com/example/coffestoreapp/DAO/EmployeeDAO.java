package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    SQLiteDatabase database;

    public EmployeeDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long addEmployee(EmployeeDTO employeeDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.EMPLOYEE_FULLNAME, employeeDTO.getFullName());
        contentValues.put(CreateDatabase.EMPLOYEE_USERNAME, employeeDTO.getUserName());
        contentValues.put(CreateDatabase.EMPLOYEE_PASSWORD, employeeDTO.getPassword());
        contentValues.put(CreateDatabase.EMPLOYEE_EMAIL, employeeDTO.getEmail());
        contentValues.put(CreateDatabase.EMPLOYEE_PHONE, employeeDTO.getPhoneNumber());
        contentValues.put(CreateDatabase.EMPLOYEE_GENDER, employeeDTO.getGender());
        contentValues.put(CreateDatabase.EMPLOYEE_BIRTHDAY, employeeDTO.getBirthday());
        contentValues.put(CreateDatabase.EMPLOYEE_ROLE_ID, employeeDTO.getRoleId());

        long check = database.insert(CreateDatabase.TABLE_EMPLOYEE, null, contentValues);
        return check;
    }

    public long editEmployee(EmployeeDTO employeeDTO, int employeeId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.EMPLOYEE_FULLNAME, employeeDTO.getFullName());
        contentValues.put(CreateDatabase.EMPLOYEE_USERNAME, employeeDTO.getUserName());
        contentValues.put(CreateDatabase.EMPLOYEE_PASSWORD, employeeDTO.getPassword());
        contentValues.put(CreateDatabase.EMPLOYEE_EMAIL, employeeDTO.getEmail());
        contentValues.put(CreateDatabase.EMPLOYEE_PHONE, employeeDTO.getPhoneNumber());
        contentValues.put(CreateDatabase.EMPLOYEE_GENDER, employeeDTO.getGender());
        contentValues.put(CreateDatabase.EMPLOYEE_BIRTHDAY, employeeDTO.getBirthday());
        contentValues.put(CreateDatabase.EMPLOYEE_ROLE_ID, employeeDTO.getRoleId());

        long check = database.update(CreateDatabase.TABLE_EMPLOYEE, contentValues,
                CreateDatabase.EMPLOYEE_ID + " = " + employeeId, null);
        return check;
    }

    public int checkAuth(String username, String password) {
        String query = "SELECT * FROM " + CreateDatabase.TABLE_EMPLOYEE + " WHERE "
                + CreateDatabase.EMPLOYEE_USERNAME + " = ? AND " + CreateDatabase.EMPLOYEE_PASSWORD + " = ?";
        int employeeId = 0;
        Cursor cursor = database.rawQuery(query, new String[] { username, password });

        try {
            if (cursor.moveToFirst()) {
                employeeId = cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ID));
            }
        } finally {
            cursor.close();
        }

        return employeeId;
    }

    public boolean checkExistEmployee() {
        String query = "SELECT * FROM " + CreateDatabase.TABLE_EMPLOYEE;
        Cursor cursor = database.rawQuery(query, null);

        try {
            return cursor.getCount() != 0;
        } finally {
            cursor.close();
        }
    }

    public List<EmployeeDTO> getListEmployee() {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TABLE_EMPLOYEE;

        Cursor cursor = database.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    EmployeeDTO employeeDTO = new EmployeeDTO.EmployeeBuilder()
                            .setFullName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_FULLNAME)))
                            .setEmail(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_EMAIL)))
                            .setGender(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_GENDER)))
                            .setBirthday(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_BIRTHDAY)))
                            .setPhoneNumber(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PHONE)))
                            .setUserName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_USERNAME)))
                            .setPassword(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PASSWORD)))
                            .setEmployId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ID)))
                            .setRoleId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ROLE_ID)))
                            .build();

                    employeeDTOS.add(employeeDTO);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return employeeDTOS;
    }

    public boolean deleteEmployee(int employeeId) {
        long check = database.delete(CreateDatabase.TABLE_EMPLOYEE, CreateDatabase.EMPLOYEE_ID + " = " + employeeId,
                null);
        return check != 0;
    }

    public EmployeeDTO getEmployeeById(int employeeId) {
        String query = "SELECT * FROM " + CreateDatabase.TABLE_EMPLOYEE + " WHERE " + CreateDatabase.EMPLOYEE_ID + " = "
                + employeeId;
        Cursor cursor = database.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                return new EmployeeDTO.EmployeeBuilder()
                        .setFullName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_FULLNAME)))
                        .setEmail(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_EMAIL)))
                        .setGender(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_GENDER)))
                        .setBirthday(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_BIRTHDAY)))
                        .setPhoneNumber(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PHONE)))
                        .setUserName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_USERNAME)))
                        .setPassword(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PASSWORD)))
                        .setEmployId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ID)))
                        .setRoleId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ROLE_ID)))
                        .build();
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    public int getRoleEmployee(int employeeId) {
        int roleId = 0;
        String query = "SELECT * FROM " + CreateDatabase.TABLE_EMPLOYEE + " WHERE " + CreateDatabase.EMPLOYEE_ID + " = "
                + employeeId;
        Cursor cursor = database.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                roleId = cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ROLE_ID));
            }
        } finally {
            cursor.close();
        }

        return roleId;
    }
}
