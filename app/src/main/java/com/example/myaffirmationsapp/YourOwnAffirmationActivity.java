package com.example.myaffirmationsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.*;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class YourOwnAffirmationActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;

    private ImageView selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_own_affirmation);

        selectedImage = findViewById(R.id.image_view_selected_image);

        Button selectImageButton = findViewById(R.id.button_select_image);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the gallery to select an image
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAffirmation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get the image URI and set it to the ImageView
            Uri imageUri = data.getData();
            selectedImage.setImageURI(imageUri);
        }
    }

    // Save Button Code starts from here
    private void saveAffirmation() {
        EditText affirmationEditText = findViewById(R.id.edit_text_affirmation);
        String affirmationText = affirmationEditText.getText().toString().trim();

        ImageView selectedImageView = findViewById(R.id.image_view_selected_image);
        Drawable selectedImageDrawable = selectedImageView.getDrawable();

        // Check if affirmation text is empty
        if (affirmationText.isEmpty()) {
            Toast.makeText(this, "Please enter an affirmation", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if selected image is null
        if (selectedImageDrawable == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the URI of the selected image
        Uri selectedImageUri = getImageUri(this, ((BitmapDrawable) selectedImageDrawable).getBitmap());

        // Save the affirmation text and selected image URI to the database using SQLite
        AffirmationDbHelper dbHelper = new AffirmationDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AffirmationDbHelper.AffirmationContract.COLUMN_NAME_TEXT, affirmationText);
        values.put(AffirmationDbHelper.AffirmationContract.COLUMN_NAME_IMAGE, selectedImageUri.toString());

        long newRowId = db.insert(AffirmationDbHelper.AffirmationContract.TABLE_NAME, null, values);

        // Show a message to the user that the affirmation has been saved
        Toast.makeText(this, "Affirmation saved!", Toast.LENGTH_SHORT).show();

        // Close the database connection
        db.close();
    }

    // Helper method to get the URI of an image
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}
