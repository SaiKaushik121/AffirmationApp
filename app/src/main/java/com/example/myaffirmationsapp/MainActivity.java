package com.example.myaffirmationsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainContract.View, AffirmationAdapter.OnAffirmationClickListener {
    private MainContract.Presenter presenter;
    private RecyclerView recyclerView;
    private AffirmationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AffirmationAdapter(this);
        recyclerView.setAdapter(adapter);

        presenter = new MainPresenter(this, getApplicationContext());
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

    @Override
    public void displayErrorMessage(String s) {
        // Implement error message display logic here
    }

    @Override
    public void showErrorMessage(String error_saving_affirmation) {
        // Implement error message display logic here
    }

    @Override
    public void showSuccessMessage(String affirmation_saved_successfully) {
        // Implement success message display logic here
    }

    @Override
    public void onAffirmationClick(int position) {
        presenter.selectAffirmation(position);
    }

    @Override
    public void displayAffirmations(List<AffirmationGenerator.Affirmation> affirmations) {
        adapter.setAffirmations(affirmations);
    }
}
