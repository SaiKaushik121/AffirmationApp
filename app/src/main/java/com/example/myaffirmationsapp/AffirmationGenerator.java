package com.example.myaffirmationsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


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

    public List<Affirmation> getAllAffirmations(Context context) {
        List<Affirmation> affirmations = new ArrayList<>();

        // Get pre-defined affirmations
        String[] preDefinedAffirmations = context.getResources().getStringArray(R.array.affirmations);
        for (String affirmationText : preDefinedAffirmations) {
            Affirmation affirmation = new Affirmation(affirmationText, R.drawable.background);
            affirmations.add(affirmation);
        }

        // Get user-defined affirmations
        List<String> userAffirmations = AffirmationDbHelper.getInstance(context).getUserAffirmations();
        for (String affirmationText : userAffirmations) {
            Affirmation affirmation = new Affirmation(affirmationText, R.drawable.user_defined_image);
            affirmations.add(affirmation);
        }

        return affirmations;
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

