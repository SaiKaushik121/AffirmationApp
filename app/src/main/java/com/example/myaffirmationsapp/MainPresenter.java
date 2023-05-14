package com.example.myaffirmationsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myaffirmationsapp.AffirmationGenerator.Affirmation;


import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private AffirmationGenerator model;
    private AffirmationDbHelper dbHelper;
    private Context context;

    public MainPresenter(MainContract.View view, Context context) {
        this.view = view;
        this.model = new AffirmationGenerator();
        this.dbHelper = new AffirmationDbHelper(context);
        this.context = context;
    }

    @Override
    public void loadAffirmationAndImage() {
        List<AffirmationGenerator.Affirmation> affirmations = model.getAllAffirmations(context);
        if (!affirmations.isEmpty()) {
            AffirmationGenerator.Affirmation affirmation = affirmations.get((int) (Math.random() * affirmations.size()));
            view.displayAffirmation(affirmation);
        }
    }


    private AffirmationGenerator.Affirmation getRandomAffirmation(List<String> userAffirmations, List<AffirmationGenerator.Affirmation> defaultAffirmations) {
        List<AffirmationGenerator.Affirmation> allAffirmations = new ArrayList<>();

        for (String userAffirmation : userAffirmations) {
            AffirmationGenerator.Affirmation affirmation = new AffirmationGenerator.Affirmation(userAffirmation, R.drawable.user_defined_image);
            allAffirmations.add(affirmation);
        }

        allAffirmations.addAll(defaultAffirmations);

        int randomIndex = (int) (Math.random() * allAffirmations.size());
        return allAffirmations.get(randomIndex);
    }


    @Override
    public void selectAffirmation(int position) {
        List<String> userAffirmations = getUserAffirmations(); // Get user-defined affirmations
        List<AffirmationGenerator.Affirmation> defaultAffirmations = model.getAllAffirmations(context); // Get default affirmations

        if (position < userAffirmations.size()) {
            // User-defined affirmation is selected
            String selectedAffirmation = userAffirmations.get(position);
            view.displayAffirmation(new AffirmationGenerator.Affirmation(selectedAffirmation, R.drawable.user_defined_image));
        } else if (position < userAffirmations.size() + defaultAffirmations.size()) {
            // Default affirmation is selected
            int defaultPosition = position - userAffirmations.size();
            AffirmationGenerator.Affirmation selectedAffirmation = defaultAffirmations.get(defaultPosition);
            view.displayAffirmation(selectedAffirmation);
        }
    }


    public void saveUserAffirmation(String text) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AffirmationDbHelper.AffirmationContract.COLUMN_NAME_TEXT, text);
        long newRowId = db.insert(AffirmationDbHelper.AffirmationContract.TABLE_NAME, null, values);
        if (newRowId == -1) {
            view.showErrorMessage("Error saving affirmation");
        } else {
            AffirmationGenerator.addUserDefinedAffirmation(text, R.drawable.background11);
            view.showSuccessMessage("Affirmation saved successfully");
        }
    }

    public List<String> getUserAffirmations() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {AffirmationDbHelper.AffirmationContract.COLUMN_NAME_TEXT};
        String sortOrder = AffirmationDbHelper.AffirmationContract.COLUMN_NAME_TEXT + " ASC";
        Cursor cursor = db.query(
                AffirmationDbHelper.AffirmationContract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List<String> userAffirmations = new ArrayList<>();
        while (cursor.moveToNext()) {
            String affirmation = cursor.getString(cursor.getColumnIndexOrThrow(AffirmationDbHelper.AffirmationContract.COLUMN_NAME_TEXT));
            userAffirmations.add(affirmation);
        }
        cursor.close();
        return userAffirmations;
    }

}
