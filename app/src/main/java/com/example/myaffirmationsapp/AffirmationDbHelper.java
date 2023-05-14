package com.example.myaffirmationsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.List;

public class AffirmationDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "affirmations.db";
    private static final int DATABASE_VERSION = 1;
    private static AffirmationDbHelper instance;

    public static synchronized AffirmationDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AffirmationDbHelper(context.getApplicationContext());
        }
        return instance;
    }

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
    public List<String> getUserAffirmations() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {AffirmationContract.COLUMN_NAME_TEXT};
        String sortOrder = AffirmationContract.COLUMN_NAME_TEXT + " ASC";
        Cursor cursor = db.query(
                AffirmationContract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List<String> userAffirmations = new ArrayList<>();
        while (cursor.moveToNext()) {
            String affirmation = cursor.getString(cursor.getColumnIndexOrThrow(AffirmationContract.COLUMN_NAME_TEXT));
            userAffirmations.add(affirmation);
        }
        cursor.close();
        return userAffirmations;
    }
}
