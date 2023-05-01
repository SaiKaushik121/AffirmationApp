package com.example.myaffirmationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this,getApplicationContext());
        presenter.loadAffirmationAndImage();
    }

    @Override
    public void displayAffirmation(AffirmationGenerator.Affirmation affirmation) {
        TextView affirmationTextView = findViewById(R.id.affirmation_text_view);
        affirmationTextView.setText(affirmation.getText());

        displayBackgroundImage(affirmation.getImageId());
    }

    @Override
    public void displayBackgroundImage(int imageId) {
        ImageView backgroundImageView = findViewById(R.id.background_image);
        backgroundImageView.setImageResource(imageId);
    }
    public void displayErrorMessage(String s) {
        // Implement error message display logic here
    }

    @Override
    public void showErrorMessage(String error_saving_affirmation) {

    }

    @Override
    public void showSuccessMessage(String affirmation_saved_successfully) {

    }
}


