package com.example.myaffirmationsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AffirmationGenerator {

    private static List<Affirmation> defaultAffirmations = new ArrayList<Affirmation>() {{
        add(new Affirmation("I am worthy and deserving of love and respect.", R.drawable.background11));
        add(new Affirmation("I believe in myself and my abilities.", R.drawable.background12));
        add(new Affirmation("I am confident and capable of achieving my goals.", R.drawable.background13));
        add(new Affirmation("I am worthy of success and happiness.", R.drawable.background14));
        add(new Affirmation("I choose to focus on the positive in my life.", R.drawable.background15));
        add(new Affirmation("I am grateful for all the good things in my life.", R.drawable.background16));
        add(new Affirmation("I am capable of overcoming any obstacle.", R.drawable.background17));
    }};

    private static List<Affirmation> userDefinedAffirmations = new ArrayList<>();



    public static void addUserDefinedAffirmation(String text, int imageId) {
        userDefinedAffirmations.add(0, new Affirmation(text, imageId));
    }

    public static List<Affirmation> getAllAffirmations(Context context) {
        // Retrieve user-defined affirmations from the database
        AffirmationDbHelper dbHelper = new AffirmationDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                AffirmationDbHelper.AffirmationContract.COLUMN_NAME_TEXT,
                AffirmationDbHelper.AffirmationContract.COLUMN_NAME_IMAGE
        };
        Cursor cursor = db.query(
                AffirmationDbHelper.AffirmationContract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        userDefinedAffirmations.clear();
        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(AffirmationDbHelper.AffirmationContract.COLUMN_NAME_TEXT));
            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(AffirmationDbHelper.AffirmationContract.COLUMN_NAME_IMAGE));
            userDefinedAffirmations.add(new Affirmation(text, imageId));
        }
        cursor.close();
        db.close();
        dbHelper.close();

        List<Affirmation> allAffirmations = new ArrayList<>();
        allAffirmations.addAll(userDefinedAffirmations);
        allAffirmations.addAll(defaultAffirmations);
        return allAffirmations;
    }

    public static class Affirmation {
        private String text;
        private int imageId;

        public Affirmation(String text, int imageId) {
            this.text = text;
            this.imageId = imageId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }
}
