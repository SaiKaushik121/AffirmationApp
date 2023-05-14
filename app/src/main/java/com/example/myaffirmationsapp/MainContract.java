package com.example.myaffirmationsapp;

import com.example.myaffirmationsapp.AffirmationGenerator;

import java.util.List;

public interface MainContract {

    interface View {
        void displayAffirmation(AffirmationGenerator.Affirmation affirmation);

        void displayBackgroundImage(int imageId);

        void displayErrorMessage(String s);

        void showErrorMessage(String error_saving_affirmation);

        void showSuccessMessage(String affirmation_saved_successfully);

        void onAffirmationClick(int position);

        void displayAffirmations(List<AffirmationGenerator.Affirmation> affirmations);
    }

    interface Presenter {
        void loadAffirmationAndImage();

        void selectAffirmation(int position);
    }

    interface Model {
        List<AffirmationGenerator.Affirmation> getAllAffirmations();
    }
}
