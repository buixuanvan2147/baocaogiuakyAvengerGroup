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
        String imageBase64 = getIntent().getStringExtra("imageBase64");
        String soundUrl = getIntent().getStringExtra("soundUrl");
        String folderId = getIntent().getStringExtra("folderId");
        String cardId = getIntent().getStringExtra("cardId");

        nameTextView.setText(name);
        title.setText(name);
        translationTextView.setText(translation);

        if (imageBase64 != null && !imageBase64.isEmpty()) {
            try {
                byte[] decodedString = android.util.Base64.decode(imageBase64, android.util.Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                if (decodedByte != null) {
                    animalImageView.setImageBitmap(decodedByte);
                } else {
                    Log.e("DetailActivity", "Decoded Bitmap is null!");
                }
            } catch (IllegalArgumentException e) {
                Log.e("DetailActivity", "Base64 decoding failed: " + e.getMessage());
            }
        } else {
            Log.e("DetailActivity", "imageBase64 is null or empty!");
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
                // Xóa dữ liệu từ Firebase Realtime Database
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
            intent.putExtra("imageBase64", getIntent().getStringExtra("imageBase64"));
            intent.putExtra("soundUrl", getIntent().getStringExtra("soundUrl"));
            intent.putExtra("folderId", folderId); // Truyền folderId
            intent.putExtra("cardId", cardId); // Truyền cardId
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
            String updatedImageBase64 = data.getStringExtra("imageBase64");

            nameTextView.setText(updatedName);
            translationTextView.setText(updatedTranslation);

            if (updatedImageBase64 != null && !updatedImageBase64.isEmpty()) {
                byte[] decodedString = android.util.Base64.decode(updatedImageBase64, android.util.Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                animalImageView.setImageBitmap(decodedByte);
            }

            // Cập nhật dữ liệu lên Firebase Realtime Database
            String folderId = getIntent().getStringExtra("folderId");
            String cardId = getIntent().getStringExtra("cardId");
            updateFlashcardInFirebase(folderId, cardId, updatedName, updatedTranslation, updatedImageBase64);
        }
    }

    private void updateFlashcardInFirebase(String folderId, String cardId, String name, String translation, String imageBase64) {
        DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("folders")
                .child(folderId)
                .child("cards")
                .child(cardId);

        Map<String, Object> update = new HashMap<>();
        update.put("name", name);
        update.put("description", translation);
        update.put("imageBase64", imageBase64);

        cardRef.updateChildren(update)
                .addOnSuccessListener(aVoid -> {
                    // Cập nhật thành công
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("translation", translation);
                    intent.putExtra("imageBase64", imageBase64);
                    setResult(RESULT_OK, intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi
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
                    // Xóa thành công
                    Intent intent = new Intent();
                    intent.putExtra("delete", true);
                    intent.putExtra("name", nameTextView.getText().toString());
                    setResult(DELETE_REQUEST_CODE, intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi
                    e.printStackTrace();
                });
    }
}
