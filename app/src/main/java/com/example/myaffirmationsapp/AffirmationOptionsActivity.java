package com.example.myaffirmationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class AffirmationOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirmation_options);

        Button buttonAffirmationOfTheDay = findViewById(R.id.button_affirmation_of_the_day);
        buttonAffirmationOfTheDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmationOptionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button buttonYourOwnAffirmation = findViewById(R.id.button_my_own_affirmation);
        buttonYourOwnAffirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmationOptionsActivity.this, YourOwnAffirmationActivity.class);
                startActivity(intent);
            }
        });




    }
}
