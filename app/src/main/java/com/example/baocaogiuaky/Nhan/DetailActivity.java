package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private TextView nameTextView, title;
    private TextView translationTextView;
    private ImageView animalImageView;
    private static final int EDIT_REQUEST_CODE = 1;
    private static final int DELETE_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_flashcards);

        nameTextView = findViewById(R.id.show_word);
        translationTextView = findViewById(R.id.show_meaning);
        animalImageView = findViewById(R.id.imageview);
        title = findViewById(R.id.title);

        String name = getIntent().getStringExtra("name");
        String translation = getIntent().getStringExtra("translation");
        String imagePath = getIntent().getStringExtra("imagePath");
        String soundUrl = getIntent().getStringExtra("soundUrl");
        String folderId = getIntent().getStringExtra("folderId");
        String cardId = getIntent().getStringExtra("cardId");

        nameTextView.setText(name);
        title.setText(name);
        translationTextView.setText(translation);

        if (imagePath != null && !imagePath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                animalImageView.setImageBitmap(bitmap);
            } else {
                Log.e("DetailActivity", "Failed to decode image from path: " + imagePath);
            }
        } else {
            Log.e("DetailActivity", "imagePath is null or empty!");
        }

        Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            View customDialogView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
            builder.setView(customDialogView);

            AlertDialog alertDialog = builder.create();

            Button buttonDelete = customDialogView.findViewById(R.id.button_delete);
            Button buttonCancel = customDialogView.findViewById(R.id.button_cancel);

            buttonDelete.setOnClickListener(v1 -> {
                
                deleteFlashcardFromFirebase(folderId, cardId);
                alertDialog.dismiss();
            });

            buttonCancel.setOnClickListener(v12 -> alertDialog.dismiss());

            alertDialog.show();
        });

        Button btnEdit = findViewById(R.id.button_edit);
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, EditActivity.class);
            intent.putExtra("name", nameTextView.getText().toString());
            intent.putExtra("translation", translationTextView.getText().toString());
            intent.putExtra("imagePath", imagePath);
            intent.putExtra("soundUrl", soundUrl);
            intent.putExtra("folderId", folderId); 
            intent.putExtra("cardId", cardId); 
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        });

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("name");
            String updatedTranslation = data.getStringExtra("translation");
            String updatedImagePath = data.getStringExtra("imagePath");

            nameTextView.setText(updatedName);
            translationTextView.setText(updatedTranslation);

            if (updatedImagePath != null && !updatedImagePath.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(updatedImagePath);
                if (bitmap != null) {
                    animalImageView.setImageBitmap(bitmap);
                } else {
                    Log.e("DetailActivity", "Failed to decode updated image from path: " + updatedImagePath);
                }
            }

            
            String folderId = getIntent().getStringExtra("folderId");
            String cardId = getIntent().getStringExtra("cardId");
            updateFlashcardInFirebase(folderId, cardId, updatedName, updatedTranslation, updatedImagePath);
        }
    }

    private void updateFlashcardInFirebase(String folderId, String cardId, String name, String translation, String imagePath) {
        DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("folders")
                .child(folderId)
                .child("cards")
                .child(cardId);

        Map<String, Object> update = new HashMap<>();
        update.put("name", name);
        update.put("description", translation);
        update.put("imagePath", imagePath);

        cardRef.updateChildren(update)
                .addOnSuccessListener(aVoid -> {
                    
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("translation", translation);
                    intent.putExtra("imagePath", imagePath);
                    setResult(RESULT_OK, intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    
                    e.printStackTrace();
                });
    }

    private void deleteFlashcardFromFirebase(String folderId, String cardId) {
        DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("folders")
                .child(folderId)
                .child("cards")
                .child(cardId);

        cardRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    
                    Intent intent = new Intent();
                    intent.putExtra("delete", true);
                    intent.putExtra("name", nameTextView.getText().toString());
                    setResult(DELETE_REQUEST_CODE, intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    
                    e.printStackTrace();
                });
    }
}
