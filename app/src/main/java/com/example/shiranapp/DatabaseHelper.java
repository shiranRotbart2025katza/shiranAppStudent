package com.example.shiranapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String TABLE_QUOTES = "quotes";
    private static final String COLUMN_QUOTE_ID = "quote_id";
    private static final String COLUMN_QUOTE_TEXT = "quote_text";
    private static final String COLUMN_QUOTE_USER_ID = "user_id"; // מזהה המשתמש

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // יצירת טבלת משתמשים
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_USERNAME + " TEXT UNIQUE, "
                + COLUMN_USER_EMAIL + " TEXT UNIQUE, "
                + COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // יצירת טבלת ציטוטים
        String createQuotesTable = "CREATE TABLE " + TABLE_QUOTES + " ("
                + COLUMN_QUOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_QUOTE_TEXT + " TEXT, "
                + COLUMN_QUOTE_USER_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_QUOTE_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(createQuotesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // פונקציה להוספת משתמש חדש
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);  // רצוי להשתמש בהצפנת סיסמאות

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // אם ההכנסה הצליחה, נחזיר true
    }

    // פונקציה לבדיקה אם המשתמש קיים (התחברות)
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE "
                + COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?", new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // פונקציה להחזיר את שם המשתמש לפי המייל
    public String getUsernameByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_USERNAME + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME));
            cursor.close();
            db.close();
            return username;
        }

        cursor.close();
        db.close();
        return null;
    }

    // פונקציה להוספת ציטוט
    public boolean addQuote(String quoteText, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUOTE_TEXT, quoteText);
        values.put(COLUMN_QUOTE_USER_ID, userId); // עדכון למזהה המשתמש החדש

        long result = db.insert(TABLE_QUOTES, null, values);
        db.close();
        return result != -1; // אם ההכנסה הצליחה, נחזיר true
    }

    // פונקציה לקבלת כל הציטוטים
    public Cursor getAllQuotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUOTES, null);
    }
}
