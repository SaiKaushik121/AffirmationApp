package com.example.myaffirmationsapp;

import java.util.List;

public interface MainContract {

    interface View {
        void displayAffirmation(AffirmationGenerator.Affirmation affirmation);

        void displayBackgroundImage(int imageId);

        void displayErrorMessage(String s);

        void showErrorMessage(String error_saving_affirmation);

        void showSuccessMessage(String affirmation_saved_successfully);
    }

    interface Presenter {
        void loadAffirmationAndImage();
    }

    interface Model {
        List<AffirmationGenerator.Affirmation> getAllAffirmations();
    }
}
