package com.example.chantsdesperance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "chantsdesperance.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "C:\\Users\\Debbie\\AndroidStudioProjects\\ChantsdEsperance\\app\\src\\androidTest\\assets";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Database schema upgrade code
        // You can copy the ALTER TABLE statements from your Python script
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        // Create the full path to the database file
        String path = DATABASE_PATH + DATABASE_NAME;

        // Create the directory if it doesn't exist
        File dbDir = new File(DATABASE_PATH);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        // Open the database connection
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

}