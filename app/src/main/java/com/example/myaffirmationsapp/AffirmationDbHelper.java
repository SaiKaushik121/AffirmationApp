package com.example.myaffirmationsapp;

import android.content.Context;
import android.database.sqlite.*;

public class AffirmationDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "affirmations.db";
    private static final int DATABASE_VERSION = 1;

    public AffirmationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the affirmations table
        db.execSQL(AffirmationContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the affirmations table if it exists
        db.execSQL(AffirmationContract.SQL_DELETE_ENTRIES);
        // Create the affirmations table
        onCreate(db);
    }

    // Define the AffirmationContract class
    public static class AffirmationContract {
        // Define the table schema
        public static final String TABLE_NAME = "affirmations";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_IMAGE = "image_url";

        // Define the SQL statements to create and delete the table
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_TEXT + " TEXT," +
                        COLUMN_NAME_IMAGE + " BLOB)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
